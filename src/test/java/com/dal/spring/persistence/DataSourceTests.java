package com.dal.spring.persistence;

import com.dal.spring.RootConfig;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Log4j
public class DataSourceTests  {

    @Setter(onMethod_ = {@Autowired})
    private DataSource dataSource;

    @Test
    public void testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            log.info(connection);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}