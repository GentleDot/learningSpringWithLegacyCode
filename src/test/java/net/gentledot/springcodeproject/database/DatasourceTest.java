package net.gentledot.springcodeproject.database;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatasourceTest {

    @Inject
    private DataSource ds;

    @Test
    void testConnection() {
        try (Connection con = ds.getConnection()) {
            System.out.println(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
