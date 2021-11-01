package ru.itis.dis.lab6;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/")
public class Servlet1 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("groupp","root servlet");
        request.setAttribute("date", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));


        try {
            request.getRequestDispatcher("index.ftlh").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
