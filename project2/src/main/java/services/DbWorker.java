package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA
 * Date: 04.11.2021
 * Time: 3:04 AM
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
            Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres");
            return conn;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
