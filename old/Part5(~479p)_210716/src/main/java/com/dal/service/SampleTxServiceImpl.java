package com.dal.service;

import com.dal.mapper.Sample1Mapper;
import com.dal.mapper.Sample2Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j
@RequiredArgsConstructor
public class SampleTxServiceImpl implements SampleTxService {

    private final Sample1Mapper mapper1;
    private final Sample2Mapper mapper2;

    @Transactional
    @Override
    public void addData(String value) {
        log.info("mapper1....................");
        mapper1.insertCol1(value);

        log.info("mapper2....................");
        mapper2.insertCol2(value);

        log.info("end........................");
    }
}
