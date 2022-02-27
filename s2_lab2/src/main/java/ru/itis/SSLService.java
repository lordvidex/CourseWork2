package ru.itis;

import ru.itis.models.Subject;
import ru.itis.dis.s2lab1.annotations.*;

import java.io.*;

/**
 * Created by IntelliJ IDEA
 * Date: 26.02.2022
 * Time: 10:10 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Component
public class SSLService {

    @Inject
    private SSLConfig config;

    private String folderName() {
        String _default = System.getProperty("user.home")+"/Desktop/certificates";
        if (config == null) {
            return _default;
        }

        String _folder = config.getDirectory();
        if (_folder == null) {
            return _default;
        }
        return _folder;
    }

    public void createCertifyingCenter(Subject center) throws IOException {
        String cmd = String.format("openssl genrsa -out %s/center/%s.key 2048 && " +
                        "openssl req -x509 -utf8 -new -key %s/center/%s.key -days 10000 -out %s/center/%s.crt -subj '%s'",
                folderName(),
                center.certificateName,
                folderName(),
                center.certificateName,
                folderName(),
                center.certificateName,
                center.abbr());
        System.out.println(cmd);
        runCommand(cmd);
    }

    public void createUserWithCenter(String centerCertificate, String centerKey, Subject userSubject) throws IOException {
        String cmd = String.format("openssl genrsa -out %s/user/%s.key 2048 && " +
                "openssl req -new -utf8 -key %s/user/%s.key -out %s/user/%s.csr -subj '%s' && " +
                "openssl x509 -req -in %s/user/%s.csr -CA %s -CAkey %s -CAcreateserial -out %s/user/%s.crt -days 365",
                folderName(),
                userSubject.certificateName,
                folderName(),
                userSubject.certificateName,
                folderName(),
                userSubject.certificateName,
                userSubject.abbr(),
                folderName(),
                userSubject.certificateName,
                centerCertificate,
                centerKey,
                folderName(),
                userSubject.certificateName);
        runCommand(cmd);

    }

    /* checks if the directory at "folderName()" is created */
    private void createCertificateFiles() {
        File f = new File(folderName()+"/user");
        if (!f.exists()) {
            f.mkdir();
        }
        f = new File(folderName()+"/center");
        if (!f.exists()) {
            f.mkdir();
        }
    }

    /* reads the content of an inputStream to the System.out */
    private void readStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
    }

    private void runCommand(String command) throws IOException {
        createCertificateFiles();
        ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command).directory(new File(folderName()));
        Process process = processBuilder.start();
        readStream(process.getInputStream());
    }


}
