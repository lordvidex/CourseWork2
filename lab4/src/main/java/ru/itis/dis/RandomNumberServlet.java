package ru.itis.dis;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * Date: 25.10.2021
 * Time: 10:15 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class RandomNumberServlet extends HttpServlet {

    private String getName(HttpServletRequest req) {
        var cookies = Arrays.stream(req.getCookies()).takeWhile(c -> c.getName().equals("firstName") || c.getName().equals("lastName")).collect(Collectors.toMap(Cookie::getName, Function.identity()));
        String name = "Anonymous";
        if(cookies.size() > 0) {
            name = cookies.get("firstName").getValue()+" "+cookies.get("lastName").getValue();
        }
        return name;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int generatedNumber = new Random().nextInt(20)+1;
        String name = getName(req);
        resp.getWriter().println("The random generated number for user "+name+" equals " + generatedNumber);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = getName(req);
        resp.getWriter().println(name+" said "+req.getReader().readLine());
    }
}
