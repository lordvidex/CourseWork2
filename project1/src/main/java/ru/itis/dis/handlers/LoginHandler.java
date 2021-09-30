package ru.itis.dis.handlers;

import ru.itis.dis.HttpHandler;
import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;

import java.io.IOException;

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
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        res.setHeader("Content-Type", "application/json");
        res.setBody("{\"result\": \"Success\"}");
        res.send();
    }
}
