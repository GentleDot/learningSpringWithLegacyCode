package net.gentledot.springcodeproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//@ExtendWith({SpringExtension.class})
@SpringBootTest
public class DatasourceTest {

    @Autowired
    private DataSource ds;

    @Test
    void testConnection() {
        try(Connection con = ds.getConnection()) {
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
