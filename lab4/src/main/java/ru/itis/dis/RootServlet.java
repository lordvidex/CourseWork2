package ru.itis.dis;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RootServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
//        String message;
//        var cookies = Arrays.stream(request.getCookies())
//                .collect(Collectors.toMap(Cookie::getName, Function.identity()));
//        if(cookies.get("firstName") != null || cookies.get("lastName")!=null) {
//            message = "You are logged in with the details:\nFirst name: "
//                    +cookies.get("firstName")
//                    +"\n"+"Second name: "+cookies.get("lastName");
//        } else {
//            message = ;
//        }
        var cookies = Arrays.stream(req.getCookies())
                .collect(Collectors.toMap(Cookie::getName, Function.identity()));
        String name = "";
        if(cookies.get("firstName") != null){
            name+=cookies.get("firstName")+" ";
        }

        if (cookies.get("lastName") != null) {
            name+= cookies.get("lastName");
        }
        if (name.trim().isEmpty()) {
            req.setAttribute("message", "You don't have cookies and your sessions have not been saved!");
        } else {
            req.setAttribute("message","You are logged in with the alias: "+name);
        }
        req.getRequestDispatcher("index.ftlh").forward(req,res);
    }
}