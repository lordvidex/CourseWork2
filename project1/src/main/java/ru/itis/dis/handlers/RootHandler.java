package ru.itis.dis.handlers;

import ru.itis.dis.HttpHandler;
import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.utils.FileToStringReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        FileToStringReader fsr = new FileToStringReader("project1/src/main/webapp/WEB-INF/views/index.html");

        // return the body
        res.setBody(fsr.readToString());
        res.send();
    }
}
