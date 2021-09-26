package ru.itis.dis.handlers;

import ru.itis.dis.HttpHandler;
import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.utils.FileToStringReader;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 1:36 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class ErrorHandler implements HttpHandler {
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        String errorHtml = new FileToStringReader("project1/src/main/webapp/WEB-INF/views/error.html")
                .readToString();
        res.setBody(errorHtml);
        res.send();
    }
}
