package com.example.Controller;

import com.example.Model.Employee;
import com.example.Service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/welcome")
@AllArgsConstructor
@Slf4j
public class HomeController {

    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployeesList() {
        log.info("Get employees list");
        return employeeService.getEmployeesList();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }
}
