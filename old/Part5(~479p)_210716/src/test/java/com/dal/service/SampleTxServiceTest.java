package com.dal.service;

import com.dal.config.RootConfig;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Log4j
@ContextConfiguration(classes = {RootConfig.class})
public class SampleTxServiceTest {

    @Setter(onMethod_ = @Autowired)
    private SampleTxService service;

    @Test
    public void testLong() {
        String str = "Starry\r\n" +
                "Starry night\r\n" +
                "Paint your palette blue and grey\r\n" +
                "Look out on a summer's day";

        log.info(str.getBytes().length);

        service.addData(str);
    }
}
