package com.tw.ankita.reactivespringmongoexample.sample;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, Long> {
}
