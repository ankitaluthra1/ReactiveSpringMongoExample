package com.tw.ankita.reactivespringmongoexample;

import com.tw.ankita.reactivespringmongoexample.employee.Employee;
import com.tw.ankita.reactivespringmongoexample.employee.EmployeeRepository;
import com.tw.ankita.reactivespringmongoexample.feedback.FeedBack;
import com.tw.ankita.reactivespringmongoexample.feedback.FeedbackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    @Bean
    @Profile("!test")
    CommandLineRunner saveEmployees(EmployeeRepository employeeRepository,FeedbackRepository feedbackRepository) {
        return args ->{
                employeeRepository.deleteAll().subscribe(null, null, () -> {
                    Stream.of(new Employee(1L, "A"), new Employee(2L, "B"),
                            new Employee(3L, "C"),
                            new Employee(4L, "D"))
                            .forEach(emp -> employeeRepository
                                    .save(emp)
                                    .subscribe(e -> System.out.println(e)));
                });
                feedbackRepository.deleteAll();
                Stream.of(new FeedBack(UUID.randomUUID().toString(),"test comments 1",1L),
                        new FeedBack(UUID.randomUUID().toString(),"test comments 2",2L),
                        new FeedBack(UUID.randomUUID().toString(),"test comments 3",3L),
                        new FeedBack(UUID.randomUUID().toString(),"test comments 4",4L)).forEach(f->feedbackRepository.save(f));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
