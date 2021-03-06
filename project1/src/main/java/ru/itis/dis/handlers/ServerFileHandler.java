package ru.itis.dis.handlers;

import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA
 * Date: 30.09.2021
 * Time: 3:17 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class ServerFileHandler implements HttpHandler {
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        final Path path = Paths.get(Constants.htmlResPath + req.getPath());
        if(Files.exists(path)) {
            res.setBody(Files.readString(path));
        } else {
            res.setBody(Files.readString(Paths.get(Constants.htmlResPath+"error.html")));
        }
        res.send();
    }
}

