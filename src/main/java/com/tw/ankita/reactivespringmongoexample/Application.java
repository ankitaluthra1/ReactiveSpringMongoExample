package com.tw.ankita.reactivespringmongoexample;

import com.tw.ankita.reactivespringmongoexample.sample.Employee;
import com.tw.ankita.reactivespringmongoexample.sample.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    @Bean
    CommandLineRunner saveEmployees(EmployeeRepository employeeRepository) {
        return args ->
                employeeRepository.deleteAll().subscribe(null, null, () -> {
                    Stream.of(new Employee(1L, "A"), new Employee(2L, "B"),
                            new Employee(3L, "C"),
                            new Employee(4L, "D"))
                            .forEach(emp -> employeeRepository
                                    .save(emp)
                                    .subscribe(e -> System.out.println(e)));
                });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
