package com.pcfz.employee_management.controller;

import com.pcfz.employee_management.domain.Employee;
import com.pcfz.employee_management.domain.EmployeeDTO;
import com.pcfz.employee_management.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pcfz.employee_management.service.EmployeeService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Employee Controller for REST APIs
 * All the Employees related APIs are defined in this controller.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {
    @Autowired
    private EmployeeService employeeService;

    // Fixed departments list for dropdown
    private final List<String> departments = List.of("Admin", "IT", "HR", "Sales");

    /**
     * API to fetch and list all the employees
     * @return all the employees
     */
    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDTO>> fetchAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDtoList =
                employees.stream()
                        .map(EmployeeMapper::toEmployeeDTO)
                        .toList();
        return ResponseEntity.ok(employeeDtoList);
    }

    /**
     * View Employee Details By Employee ID
     * @param employeeId employee ID to search
     * @return Employee data of given employeeId
     */
    @GetMapping("/view/{employeeId}")
    public ResponseEntity<EmployeeDTO> fetchEmployeeById(@PathVariable Long employeeId) {
        final Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return ResponseEntity.ok(EmployeeMapper.toEmployeeDTO(employee));
    }

    /**
     * Add new employee
     * @param employee employee to be added
     * @return new added employee record
     */
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody Employee employee) {
        final Employee newEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EmployeeMapper.toEmployeeDTO(newEmployee));
    }

    /**
     * updateEmployee existing employee with given employee data
     * @param employeeId employeeId which needs to be updated
     * @param employee new employee data
     * @return updated employee data
     */
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long employeeId,
                                                   @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employee);
        return ResponseEntity.ok(EmployeeMapper.toEmployeeDTO(updatedEmployee));
    }

    /**
     * Delete Employee
     * @param employeeId employee ID to delete
     */
    @DeleteMapping("/delete/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        ResponseEntity.ok("Employee deleted successfully.");
    }
}
