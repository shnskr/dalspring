package com.dal.persistence;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.fail;

@Log4j
public class JDBCTests {

    static {
        try {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {
//        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:XE";
        String username = "dal";
        String password = "123777";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            log.info(connection);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
