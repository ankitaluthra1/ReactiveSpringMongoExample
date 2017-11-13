package com.tw.ankita.reactivespringmongoexample.employee;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, Long> {
}
