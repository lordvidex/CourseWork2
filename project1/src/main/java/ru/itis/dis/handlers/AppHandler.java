package ru.itis.dis.handlers;

import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
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
public class AppHandler implements HttpHandler {
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        res.setBody(Files.readString(Paths.get(Constants.htmlResPath+"app.html")));
        res.send();
    }
}
