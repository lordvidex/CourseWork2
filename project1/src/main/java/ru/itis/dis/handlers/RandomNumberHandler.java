package ru.itis.dis.handlers;

import ru.itis.dis.HttpRequest;
import ru.itis.dis.HttpResponse;
import ru.itis.dis.Session;

import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * Date: 01.10.2021
 * Time: 3:48 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class RandomNumberHandler implements HttpHandler {
    @Override
    public void process(HttpRequest req, HttpResponse res) throws IOException {
        // get the user session and his name from this session
        // get the user's name
        String fullName;
        Session userSession = req.getSession();
        if (userSession == null) {
            fullName = "Anonymous";
        } else {
            fullName = userSession.firstName + " " + userSession.lastName;
        }

        // set the content-type header
        res.setHeader("Content-Type", "text/plain");

        // process get/post request
        if (req.getMethod().equals("GET")) {
            get(req,res,fullName);
        } else if (req.getMethod().equals("POST")) {
            post(req,res,fullName);
        }
    }


    // get запрос
    private void get(HttpRequest req, HttpResponse res, String fullName) throws IOException {

        int generatedNumber = new Random().nextInt(20)+1;

        // compose the return text
        res.setBody("The random generated number for user " + fullName + " equals " + generatedNumber);
        res.send();
    }

    // post запрос
    private void post(HttpRequest req, HttpResponse res, String fullName) throws IOException {
        String userValue = req.getBody();
        res.setBody(fullName+" said "+userValue);
        res.send();
    }
}
