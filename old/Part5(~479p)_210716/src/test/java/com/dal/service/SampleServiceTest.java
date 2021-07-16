package com.dal.service;

import com.dal.config.RootConfig;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Log4j
@ContextConfiguration(classes = {RootConfig.class})
public class SampleServiceTest {

    @Setter(onMethod_ = @Autowired)
    private SampleService service;

    @Test
    public void testClass() {
        log.info(service);
        log.info(service.getClass().getName());
    }

    @Test
    public void testAdd() throws Exception {
        log.info(service.doAdd("123", "456"));
    }

    @Test
    public void testAddError() throws Exception {
        log.info(service.doAdd("123", "ABC"));
    }
}
