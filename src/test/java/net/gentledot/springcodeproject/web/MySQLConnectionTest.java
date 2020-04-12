package net.gentledot.springcodeproject.web;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
//    private static final String DRIVER = "com.mysql.jdbc.driver"; // deprecated
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://127.0.0.1:3306/book_ex";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/book_ex?serverTimezone=UTC";
    private static final String USER = "gentledot";
    private static final String PW = "Password2020";

    @Test
    void connectionTest() throws ClassNotFoundException {
        Class.forName(DRIVER);

        try(Connection con = DriverManager.getConnection(URL, USER, PW)) {
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
