package com.example.Service;

import com.example.Model.Employee;
import com.example.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private List<Employee> empList = new ArrayList<>();
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService() {
        empList.add(new Employee(UUID.randomUUID().toString(), "yuvraj singh", "hrishab@gmail.com"));
        empList.add(new Employee(UUID.randomUUID().toString(), "ritesh sharma", "ritesh@gmail.com"));
    }

    public List<Employee> getEmployeesList() {
        return empList;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
