package com.tw.ankita.reactivespringmongoexample.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@RestController
@RequestMapping("/employees")
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
    public Mono<Employee> getById(@PathVariable Long id) throws NoSuchFieldException {
       Mono<Employee> employee = employeeRepository.findById(id);
       if( employee.block() == null)
           throw new NoSuchElementException("Employee with id "+ id + " not found");

       return employee;
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

    @PostMapping("/save")
    public Mono<Employee> saveEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

}
