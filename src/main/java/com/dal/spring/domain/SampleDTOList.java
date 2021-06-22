package com.dal.spring.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SampleDTOList {
    private List<SampleDTO> list;

    public SampleDTOList() {
        this.list = new ArrayList<>();
    }
}
