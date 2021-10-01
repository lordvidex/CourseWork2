package ru.itis.dis.handlers;

import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.Session;
import ru.itis.dis.utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 1:35 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class RootHandler implements HttpHandler {
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        res.setHeader("Content-Type", "text/html");

        // read the body from html file
        // return the body
        String result = Files.readString(Paths.get(Constants.htmlResPath+"index.html"));
        Session userSession = req.getSession();
        String replacement;
        if(userSession == null) {
            replacement=
                    "<p>You don't have cookies and your sessions have not been saved!</p>";
        } else {
            replacement="<p>You are logged in with the details:\nFirst name: "+
                    userSession.firstName+
                    "\n"+"Second name: "+userSession.lastName+"</p>";
        }
        result = result.replace("<%cookie%>",replacement);
        res.setBody(result);
        res.send();
    }
}
