package ru.itis.dis.handlers;

import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.Session;
import ru.itis.dis.utils.Constants;
import ru.itis.dis.utils.SessionSetter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA
 * Date: 30.09.2021
 * Time: 11:07 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class LoginHandler implements HttpHandler {
   final SessionSetter sessionSetter;

   public LoginHandler(SessionSetter setter){
        this.sessionSetter = setter;
   }

    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
       String body = Files.readString(Paths.get(Constants.htmlResPath+"login_result.html"));
       if(sessionSetter != null && req.getBody() != null) {
            // parse body to session and
            // add session to header
           //username=Evans&password=Evans1
            String sessionKey = sessionSetter.setSession(Session.fromString(req.getBody()));
            res.setCookie("JSESSION", sessionKey,3600);
            body = body.replace("%status%",req.getSession() == null
                    ? "You have successfully logged in!"
                    : "You have been re-authenticated");
        } else {
            body = body.replace("%status%","Failed to log in");
        }
       res.setBody(body);
        // redirect to root
        res.send();
    }
}
