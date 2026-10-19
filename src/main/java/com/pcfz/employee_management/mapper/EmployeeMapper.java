package com.pcfz.employee_management.mapper;

import com.pcfz.employee_management.domain.Employee;
import com.pcfz.employee_management.domain.EmployeeDTO;
import com.pcfz.employee_management.domain.ManagerDTO;

/**
 * EmployeeMapper class to map Employee data to EmployeeDTO
 * This is used for the REST APIs responses
 */
public class EmployeeMapper {

    /**
     * @param emp Employee entity response
     * @return EmployeeDTO response
     */
    public static EmployeeDTO toEmployeeDTO(Employee emp) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(emp.getId());
        employeeDTO.setFirstName(emp.getFirstName());
        employeeDTO.setLastName(emp.getLastName());
        employeeDTO.setDob(emp.getDob());
        employeeDTO.setDepartments(emp.getDepartments());
        employeeDTO.setSalary(emp.getSalary());
        employeeDTO.setProfilePhotoPath(emp.getProfilePhotoPath());

        if (emp.getManager() != null) {
            ManagerDTO managerDTO = new ManagerDTO();
            managerDTO.setId(emp.getManager().getId());
            managerDTO.setFirstName(emp.getManager().getFirstName());
            managerDTO.setLastName(emp.getManager().getLastName());
            employeeDTO.setManager(managerDTO);
        }
        return employeeDTO;
    }
}
