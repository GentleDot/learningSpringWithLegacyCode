package net.gentledot.springcodeproject.database;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySQLConnectionTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String DRIVER = "net.sf.log4jdbc.sql.jdbcapi.DriverSpy";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/book_ex";
//    private static final String URL = "jdbc:log4jdbc:mysql://127.0.0.1:3306/book_ex";
    private static final String USER = "gentledot";
    private static final String PW = "Password2020";

    @Test
    void connectionTest() throws ClassNotFoundException {
        Class.forName(DRIVER);

        try(Connection con = DriverManager.getConnection(URL, USER, PW)) {
            log.info("Connection 객체 : {}", con);

            Statement statement = con.createStatement();
            String sql = "select count(*) as cnt from tbl_member";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()) {
                log.info("카운트 결과 : {}", rs.getInt("cnt"));
            }

            statement.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
