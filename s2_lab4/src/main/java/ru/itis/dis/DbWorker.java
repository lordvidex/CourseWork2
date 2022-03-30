package ru.itis.dis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 2:33 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class DbWorker {
    private static volatile DbWorker db;
    //
    private DbWorker() {}
    //
    public static DbWorker getInstance() {
        if (db == null) {
            synchronized(DbWorker.class) {
                if (db == null) {
                    db = new DbWorker();
                }
            }
        }
        return db;
    }
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/uberdb");
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
