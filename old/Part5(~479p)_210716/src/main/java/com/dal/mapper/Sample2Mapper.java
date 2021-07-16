package com.dal.mapper;

import org.apache.ibatis.annotations.Insert;

public interface Sample2Mapper {

    @Insert("insert into tbl_sample2 (col2) values (#{data})")
    int insertCol2(String data);
}
