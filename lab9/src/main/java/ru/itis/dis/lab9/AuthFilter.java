package ru.itis.dis.lab9;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "all", urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);

        System.out.println("do filter " + req.getContextPath() + "," +req.getServletPath());

        if (!req.getServletPath().startsWith("/login.html")
                && !req.getServletPath().startsWith("/image")
                && !req.getServletPath().startsWith("/usercheck")
                && !req.getServletPath().startsWith("/resources")
                && session == null  ) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
