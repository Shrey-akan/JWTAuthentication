package com.example.Model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private String id;
    private String name;
    private String email;
    private String password;
}