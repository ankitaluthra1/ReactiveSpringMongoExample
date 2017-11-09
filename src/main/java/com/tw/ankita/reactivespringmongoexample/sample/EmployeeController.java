package com.tw.ankita.reactivespringmongoexample.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Stream;

@RestController
@RequestMapping("/sample")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/all")
    public Flux<Employee> getAll() {

        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEmployeeEvents(@PathVariable Long id) {

        return employeeRepository.findById(id).flatMapMany(e -> {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

            Flux<EmployeeEvent> employeeEventFlux = Flux.fromStream(Stream.generate(() ->
                    new EmployeeEvent(e.getName(), e.getEmployeeId(), 1L)));

            return Flux.zip(interval, employeeEventFlux).map(Tuple2::getT2);
        });

    }


}
