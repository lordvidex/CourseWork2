package ru.itis.dis;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA
 * Date: 25.10.2021
 * Time: 9:25 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        String status;
        if (firstName != null || lastName != null) {
            Cookie firstNameCookie = new Cookie("firstName", firstName);
            firstNameCookie.setMaxAge(3600);
            Cookie lastNameCookie = new Cookie("lastName", lastName);
            lastNameCookie.setMaxAge(3600);
            resp.addCookie(firstNameCookie);
            resp.addCookie(lastNameCookie);
            status = "You have successfully logged in!";
        } else {
            status  = "Failed to log in!";
        }
        req.setAttribute("status",status);
        req.getRequestDispatcher("/login_result.ftlh").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/html/login.html").forward(req,resp);
    }
}
