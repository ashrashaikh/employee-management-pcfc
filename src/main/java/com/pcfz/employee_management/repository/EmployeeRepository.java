package com.pcfz.employee_management.repository;

import com.pcfz.employee_management.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Employee table
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
