package com.tw.ankita.reactivespringmongoexample.sample;

public class EmployeeEvent {

    String name;
    Long id;
    Long timestamp;

    public EmployeeEvent(String name, Long id, Long timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    public EmployeeEvent() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
