package com.tw.ankita.reactivespringmongoexample.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Document(collection = "employees")
public class Employee {

    @Id
    private Long employeeId;
    private String name;

}
