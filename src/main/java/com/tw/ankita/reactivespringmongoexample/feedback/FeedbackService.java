package com.tw.ankita.reactivespringmongoexample.feedback;

import com.tw.ankita.reactivespringmongoexample.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FeedbackService {

    FeedbackRepository feedbackRepository;

    WebClient employeeWebClient;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
        this.employeeWebClient = WebClient.create("http://localhost:8081/employees");
    }

    public Flux<Employee> getFeedbackForAllEmployee(){
        List<FeedBack> feedbacks = feedbackRepository.findAll();

        System.out.println("before feedback in method");

        return employeeWebClient.get().uri("/all" ).exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Employee.class));

    }

    public FeedBack getFeedBackByEmployeeId(Long id){
        return feedbackRepository.findByEmployeeId(id);
    }
}
