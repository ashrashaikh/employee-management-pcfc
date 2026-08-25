package com.pcfz.employee_management.domain;

import lombok.Data;

import java.time.LocalDate;

/**
 * EmployeeDTO response
 */
@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String departments;
    private Double salary;
    private ManagerDTO manager;
}
