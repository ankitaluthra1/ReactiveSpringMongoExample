package com.tw.ankita.reactivespringmongoexample.feedback;

import com.tw.ankita.reactivespringmongoexample.employee.Employee;

public class CompleteFeedback {

    FeedBack feedBack;
    Employee employee;

    public CompleteFeedback(FeedBack feedBack, Employee employee) {

        this.feedBack = feedBack;
        this.employee = employee;
        System.out.println(" Getting feedback for "+employee.getEmployeeId());

    }

    public String getName(){
        return this.employee.getName();
    }

    public Long getEmployeeId(){
        return this.employee.getEmployeeId();
    }

    public String getFeedback(){
        return this.feedBack.getComment();
    }
}
