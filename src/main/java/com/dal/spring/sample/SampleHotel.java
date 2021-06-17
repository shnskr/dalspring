package com.dal.spring.sample;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
@Getter
@RequiredArgsConstructor
public class SampleHotel {

    @NonNull
    private Chef chef;

}
