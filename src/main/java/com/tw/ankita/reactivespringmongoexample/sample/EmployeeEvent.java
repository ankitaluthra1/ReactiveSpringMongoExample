package com.tw.ankita.reactivespringmongoexample.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmployeeEvent {

    private String name;
    private Long id;
    private Long timestamp;

}
