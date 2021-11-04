import java.sql.*;

/**
 * Created by IntelliJ IDEA
 * Date: 03.11.2021
 * Time: 9:44 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class AssignmentJDBC {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres")) {
            Statement statement = conn.createStatement();
            // drop tables if they exist
            statement.execute("DROP TABLE IF EXISTS school_group CASCADE; DROP TABLE IF EXISTS student CASCADE;");
            // create the database and connect to the database
            statement.execute("DROP DATABASE IF EXISTS jdbc_assignment; " +
                    "CREATE DATABASE jdbc_assignment;");

            //create tables
            statement.execute("CREATE TABLE school_group(" +
                    "id SERIAL NOT NULL PRIMARY KEY,"+
                    "number VARCHAR(20) NOT NULL"+
                    ")");
            statement.execute("CREATE TABLE student(" +
                    "id SERIAL NOT NULL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL,"+
                    "group_id INT REFERENCES school_group(id)"+
                    ")");

            // insert items
            statement.executeUpdate("INSERT INTO school_group(number) VALUES ('11-013'),('11-012')");
            statement.executeUpdate("INSERT INTO student(name, group_id) " +
                    "VALUES ('Evans Owamoyo', 1), ('Ahmed Khelali', 2), ('Anonymous', 1)");

            // perform queries
            ResultSet result = statement.executeQuery("SELECT student.id, student.name, sg.number from student join school_group sg on student.group_id = sg.id");
            ResultSetMetaData rmd = result.getMetaData();
            String space = "  |  ";

            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.print(rmd.getColumnName(i)+space);
            }
            System.out.print("\r\n");

            while(result.next()) {
                System.out.print(result.getInt(1)+space);
                System.out.print(result.getString(2)+space);
                System.out.print(result.getString(3)+space);
                System.out.print("\r\n");
            }
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
