package com.tw.ankita.reactivespringmongoexample.sample;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@AllArgsConstructor
//@Getter
@Document(collection = "employees")
public class Employee {

    @Id
    private Long employeeId;
    private String name;

    public Employee() {
    }

    public Employee(Long employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }
}
