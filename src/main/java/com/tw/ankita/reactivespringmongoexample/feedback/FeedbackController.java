package com.tw.ankita.reactivespringmongoexample.feedback;

import com.tw.ankita.reactivespringmongoexample.employee.Employee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping(value = "/all")
    public Flux<Employee> getAllFeedbacks(){
        return feedbackService.getFeedbackForAllEmployee();
    }

    @GetMapping(value = "/{employeeId}")
    public FeedBack getFeedForEmployee(@PathVariable Long employeeId){
        return feedbackService.getFeedBackByEmployeeId(employeeId);
    }
}
