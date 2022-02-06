package aa.trusov.keyDispatcher.controllers;

import aa.trusov.keyDispatcher.entities.Role;
import aa.trusov.keyDispatcher.entities.User;
import aa.trusov.keyDispatcher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class UsersController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam String confirmPassword, Model model, RedirectAttributes redirectAttributes){
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("errorUsername",1);
            return "registration";
        }
        System.out.println(confirmPassword);
        if (!user.getPassword().equals(confirmPassword)){
            model.addAttribute("errorPassword",1);
            return "registration";
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "registration";
        }
        if (userService.getCountUsers()==0){
            user.setRoles(Collections.singleton(Role.ADMIN));
            user.setActive(true);
        }
        else{
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(false);
        }
        userService.save(user);
        model.addAttribute("successRegistration","Пользователь зарегистрирован и ожидает активации модератором.");
        return "login";
    }

    @GetMapping("/users")
    public String showAllPairkeys(Model model) {
        //model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("users", userService.getAllUsersWithoutPassword());
        return "users";
    }

    @PostMapping("/users/activate/{id}/{active}")
    public String changeUserActive(Model model, @PathVariable(required = true) Long id, @PathVariable(required = true) boolean active){
        boolean saveActive = true;
        if (active){
            saveActive = false;
        }
        userService.changeUserActive(saveActive, id);
        model.addAttribute("success", true);
        return "redirect:/users";
    }

}
