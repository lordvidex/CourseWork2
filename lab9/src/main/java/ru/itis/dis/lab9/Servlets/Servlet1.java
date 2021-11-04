package ru.itis.dis.lab9.Servlets;

import ru.itis.dis.lab9.DAO.DriverViews;
import ru.itis.dis.lab9.DbWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/")
public class Servlet1 extends HttpServlet {
    Connection conn;
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
       conn = DbWorker.getConnection();
       Statement statement;
       ResultSet set;
        try {
            assert conn != null;
            statement = conn.createStatement();
            ArrayList<DriverViews> drivers = new ArrayList<>();

            set = statement.executeQuery("SELECT * from drivers;");

            while(set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String phone = set.getString("phone");
                int carId = set.getInt("car");
                drivers.add(new DriverViews(id,name,phone,carId));
            }

            request.setAttribute("lstDriver", drivers);
            request.getRequestDispatcher("index.ftlh").forward(request,response);

            set.close();
            statement.close();
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
