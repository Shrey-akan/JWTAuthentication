package com.example.Repository;

import com.example.Model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
//    Optional<Employee> findByEmail(String email);
    Employee findByEmail(String email);

}
