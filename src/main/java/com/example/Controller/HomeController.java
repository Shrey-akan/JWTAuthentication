package com.example.Controller;

import com.example.Model.Employee;
import com.example.Model.JwtRequest;
import com.example.Model.JwtResponse;
import com.example.Service.EmployeeService;
import com.example.jwt.JwtHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/welcome")
@AllArgsConstructor
@Slf4j
public class HomeController {

    private EmployeeService employeeService;
    private UserDetailsService userDetailsService;

    private AuthenticationManager manager;

    private JwtHelper helper;
    private JwtAuthenticationController jwtAuthenticationController;

    private final String jwtSecret="afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    private String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600 * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2592000 * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeesList() {
        log.info("Get employees list");
        return employeeService.getEmployeesList();
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        log.info("Add employee: {}", employee);
        Employee addedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedEmployee);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkEmailExistence(@RequestParam String email) {
        if (employeeService.isEmailExists(email)) {
            return ResponseEntity.ok("Email exists in the database");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist in the database");
        }
    }
    @GetMapping("/login")
    public String checking() {
        return "I am accessible without token";
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(@RequestBody Map<String, String> credentials) {
//        String email = credentials.get("email");
//        String password = credentials.get("password");
//
//        if (employeeService.isEmailAndPasswordValid(email, password)) {
//            return ResponseEntity.ok("Authentication successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
//        String email = credentials.get("email");
//        String password = credentials.get("password");
//
//        if (email == null || password == null) {
//            return ResponseEntity.badRequest().body("Email and password must be provided");
//        }
//
//        if (employeeService.isEmailAndPasswordValid(email, password)) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
//            String token = this.helper.generateToken(userDetails);
//
//            JwtResponse response = JwtResponse.builder()
//                    .jwtToken(token)
//                    .username(userDetails.getUsername()).build();
//            return ResponseEntity.ok("Authentication successful");
//        } else {
//            return ResponseEntity.status(401).body("Invalid email or password");
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password must be provided");
        }

        if (employeeService.isEmailAndPasswordValid(email, password)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = this.helper.generateToken(userDetails);

            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

}