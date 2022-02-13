package aa.trusov.keyDispatcher.controllers;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import aa.trusov.keyDispatcher.services.GeneratePairkeyService;
import aa.trusov.keyDispatcher.services.PairkeysService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;


@Controller
public class MainController {

    private PairkeysService pairkeysService;

    private GeneratePairkeyService generatePairkeyServiceImpl;

    @Autowired
    public void setGeneratePairkeyServiceImpl(GeneratePairkeyService generatePairkeyServiceImpl) {
        this.generatePairkeyServiceImpl = generatePairkeyServiceImpl;
    }

    @Autowired
    public void setPairkeysService(PairkeysService pairkeysService) {
        this.pairkeysService = pairkeysService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/pairkeys/add")
    public String addPairkeysPage(Model model) {
        Pairkeys pairkeys = pairkeysService.getNewPairkeys();
        model.addAttribute("pairkeys", pairkeys);
        return "addPairkeys";
    }

    @PostMapping("/pairkeys/add")
    public String addPairkeys(@Valid @ModelAttribute("pairkeys") Pairkeys pairkeys, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            model.addAttribute("pairkeys", pairkeys);
            return "addPairkeys";
        }
        String pairkeysFolderName = generatePairkeyServiceImpl.generateCSRAndPrivateKeyAndGetFolderNameToPairkeyFiles(pairkeys);
        pairkeys.setPairkeysFolderName(pairkeysFolderName);
        String csrText = generatePairkeyServiceImpl.getCSR(pairkeys);
        redirectAttributes.addFlashAttribute("csrText", csrText);
        String privateText = generatePairkeyServiceImpl.getPrivateKey(pairkeys);
        redirectAttributes.addFlashAttribute("privateText", privateText);
        pairkeysService.save(pairkeys);
        return "redirect:/successGenerate";
    }

    @GetMapping("/successGenerate")
    public String successGeneratePairkeys(@ModelAttribute("csrText") String csrText, @ModelAttribute("privateText") String privateText,Model model) {
        //model.addAttribute("csrText", csrText);
        //model.addAttribute("privateText", privateText);
        return "successGeneratePairkeys";
    }

    @GetMapping("/pairkeys")
    public String showAllPairkeys(Model model){
        model.addAttribute("pairkeys", pairkeysService.getAllPairkeys());
        return "pairkeys";
    }

    @GetMapping("/waitPairkeys")
    public String showWaitPairkeys(Model model){
        model.addAttribute("pairkeys", pairkeysService.getWaitPairkeys());
        return "waitPairkeys";
    }

    @DeleteMapping("/pairkeys/delete/{id}")
    public String deletePairkeys(Model model, @PathVariable(required = true) Long id, HttpServletRequest request) {
        pairkeysService.deleteById(id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/getFile")
    @ResponseBody
    public void getFilePost(@ModelAttribute("id") String pairkeysId, @ModelAttribute("filename") String fileName, HttpServletResponse response) {
            String pairkeysFolderName = pairkeysService.getPairkeysById(Long.parseLong(pairkeysId)).getPairkeysFolderName();
            String fullFileName = pairkeysFolderName + "\\" + fileName;
            try (InputStream is = new FileInputStream(fullFileName)){
                File file = new File(fullFileName);
                if (file.exists()){
                    IOUtils.copy(is, response.getOutputStream());
                    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
                    response.flushBuffer();
                }
                else {
                    System.out.println("Файл " + fullFileName + " не существует: ");
                }
            } catch (IOException ex) {
                throw new RuntimeException("IOError writing file to output stream");
            }
    }
}
