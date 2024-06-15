package com.example.Service;

import com.example.Model.Employee;
import com.example.Repository.EmployeeRepository;
import com.example.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private List<Employee> empList = new ArrayList<>();

    @Autowired
    private EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public EmployeeService( BCryptPasswordEncoder passwordEncoder , JwtHelper jwtHelper) {
        empList.add(new Employee(UUID.randomUUID().toString(), "hrishab", "hrishab@gmail.com","password"));
        empList.add(new Employee(UUID.randomUUID().toString(), "ritesh", "ritesh@gmail.com","password"));
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    public List<Employee> getEmployeesList() {
        return employeeRepository.findAll();
    }
    public Employee addEmployee(Employee employee) {
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        return employeeRepository.save(employee);
    }

    public boolean isEmailExists(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return employee != null;
    }

    public boolean isEmailAndPasswordValid(String email, String rawPassword) {
        Employee employee = employeeRepository.findByEmail(email);
        return employee != null && passwordEncoder.matches(rawPassword, employee.getPassword());
    }


}