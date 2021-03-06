package aa.trusov.keyDispatcher.services;

import aa.trusov.keyDispatcher.entities.Pairkeys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeneratePairkeyServiceImpl implements GeneratePairkeyService {

    @Value("${pathToOpenSslExe:C:\\Program Files\\OpenSSL-Win64\\bin}")
    private String pathToOpenSslExe;


    private String generateFolderName(Pairkeys p){
        String folderName = "certs\\" + LocalDate.now() + "_" + DateTimeFormatter.ofPattern("HH-mm-ss").format(LocalTime.now()) + "_" + p.getDnsName();
        return folderName;
    }

    private String generateBatFileName(Pairkeys p){
        String batFileName = LocalDate.now() + "_" + DateTimeFormatter.ofPattern("HH-mm-ss").format(LocalTime.now()) + "_" + p.getDnsName() + ".bat";
        return batFileName;
    }

    private String generateFileNameWithoutFormat(Pairkeys p){
        String fileName = p.getTypeCert().toString().toLowerCase();
        return fileName;
    }

    @Override
    public String generateCSRAndPrivateKeyAndGetFolderNameToPairkeyFiles(Pairkeys p) {
        String folderName = generateFolderName(p);
        String batFileName = generateBatFileName(p);
        String fileName = generateFileNameWithoutFormat(p);

        System.out.println("pathToOpenSslExe=" + pathToOpenSslExe);

        try(FileWriter writer = new FileWriter(batFileName, false))
        {
            String createFolderCommand = "mkdir " + folderName + "\n";
            writer.write(createFolderCommand);
            writer.flush();

            String generateCSRAndPrivateKeyCommand = "\"" + pathToOpenSslExe + "\\openssl.exe\" req " +
                    "-out \"" + folderName + "\\" + fileName + ".csr\" " +
                    "-new -newkey 2048 " +
                    "-nodes -keyout \"" + folderName + "\\" + fileName + ".key\" " +
                    "-config \"C:\\Program Files\\OpenSSL-Win64\\bin\\openssl.cfg\" " +
                    "-subj \"/C="+ p.getCountry() + "/ST='" + p.getCountryUnit() + "'/CN=" + p.getDnsName() + "/L=" + p.getCity() + "/subjectAltName='" + p.getSubjectAlternativeName() + "'/O='" + p.getOrganisation() + "'/OU='" + p.getOrganisationUnit() + "'/emailAddress=" + p.getEmail() + "\"";


            writer.write(generateCSRAndPrivateKeyCommand);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    List<String> command = new ArrayList<>();
        command.add("cmd.exe");
        command.add("/C");
        command.add(batFileName);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process proc = processBuilder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
            while ((in.readLine()) != null) {
                System.out.println("==>" + in.readLine());
            }
            in.close();
            if (proc.isAlive()) {
                proc.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folderName;
    }

    @Override
    public String getCSR(Pairkeys p) {
        StringBuilder CSRtext = new StringBuilder();
        String ls = System.getProperty("line.separator");
        try(BufferedReader reader = new BufferedReader( new FileReader (p.getPairkeysFolderName() + "\\" + generateFileNameWithoutFormat(p) + ".csr"))){
            String line;
            while( ( line = reader.readLine() ) != null ) {
                CSRtext.append( line );
                CSRtext.append( ls );
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return CSRtext.toString();
    }

    @Override
    public String getPrivateKey(Pairkeys p) {
        StringBuilder privateText = new StringBuilder();
        String ls = System.getProperty("line.separator");
        try(BufferedReader reader = new BufferedReader( new FileReader (p.getPairkeysFolderName() + "\\" + generateFileNameWithoutFormat(p) + ".key"))){
            String line;
            while( ( line = reader.readLine() ) != null ) {
                privateText.append( line );
                privateText.append( ls );
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return privateText.toString();
    }
}
