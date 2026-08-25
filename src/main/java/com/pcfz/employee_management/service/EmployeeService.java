package com.pcfz.employee_management.service;

import com.pcfz.employee_management.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.pcfz.employee_management.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get all employees
     * @return list of all the employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * getEmployeeById
     * @param employeeId employee ID to find
     * @return Employee with given ID.
     */
    public Employee getEmployeeById(final Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found for Id: " + employeeId));
    }

    /**
     * saveEmployee
     * @param employee Employee data to be added and saved to db
     * @return updated employee details
     */
    @Transactional
    public Employee saveEmployee(final Employee employee) {
        if (employee.getManager() != null && employee.getManager().getId() != null) {
            Employee manager = getEmployeeById(employee.getManager().getId());
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee
     * @param employeeId employeeId to be deleted
     */
    @Transactional
    public void deleteEmployee(final Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new RuntimeException("Employee not found with Id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

    @Transactional
    public Employee updateEmployee(final Long employeeId, final Employee employee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Employee not found for Id: " + employeeId));
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setDob(employee.getDob());
        existingEmployee.setDepartments(employee.getDepartments());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setManager(employee.getManager());

        return employeeRepository.save(existingEmployee);
    }

    /**
     * getAllManagers
     * @return list of employees who are managers
     */
    public List<Employee> getAllManagers() {
        return employeeRepository.findAll();
    }
}
