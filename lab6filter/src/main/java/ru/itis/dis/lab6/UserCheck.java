package ru.itis.dis.lab6;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/usercheck")
public class UserCheck extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username + "&" + password);

        HttpSession session = request.getSession(true);
        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setMaxInactiveInterval(60);

        request.setAttribute("groupp","11-013");
        request.setAttribute("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));


        try {
            response.sendRedirect("/lab6/index2.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
