package ru.itis.dis.lab9.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/index2.html")
public class Servlet2 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("groupp","11-013");
        request.setAttribute("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));


        try {
            request.getRequestDispatcher("index2.ftlh").forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
