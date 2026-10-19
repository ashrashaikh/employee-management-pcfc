package com.pcfz.employee_management.controller;

import com.pcfz.employee_management.domain.Employee;
import com.pcfz.employee_management.domain.EmployeeDTO;
import com.pcfz.employee_management.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.pcfz.employee_management.service.EmployeeService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Employee Management Controller
 * This is Spring MVC Controller for JSP + Tiles frontend
 */
@Controller
@RequestMapping("/employees")
public class EmployeeManagementController {
    @Autowired
    private EmployeeService employeeService;

    // Fixed departments list for dropdown
    private final List<String> departments = List.of("Admin", "IT", "HR", "Sales");

    /**
     * Lists all the employees
     * @return list-employees jsp view
     */
    @GetMapping("/list")
    public String fetchAllEmployees(Model model) {
        List<EmployeeDTO> employeeDTOList = employeeService.getAllEmployees()
                        .stream()
                                .map(EmployeeMapper::toEmployeeDTO)
                                        .toList();
        model.addAttribute("employees", employeeDTOList);
        return "list-employees";
    }

    /**
     * Shows the Add new employee form
     * Initializes a blank Employee object and populates
     * the departments and managers for the form.
     * This is used for new employee entry.
     * @param model employee model to be added
     * @return add-employee jsp view
     */
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departments);
        model.addAttribute("managers", employeeService.getAllManagers());
        return "add-employee";
    }

    /**
     * On click of Submit button on add-employee form,
     * this API will save the employees data in table
     * and list all the added employees.
     * @param employee Employee model to be saved
     * @return saves the employee and redirects to
     * list-employee view to list all the employees
     */
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee,
                               @RequestParam(value = "photoFile", required = false) MultipartFile profilePhotoFile,
                               Model model) {
        try {
            employeeService.saveEmployee(employee, profilePhotoFile);
            return "redirect:/employees/list";
        } catch (RuntimeException e) {
          model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("departments", departments);
            model.addAttribute("managers", employeeService.getAllManagers());
            return "add-employee";
        }
    }

    /**
     * View Employee Details By Employee ID
     * @param employeeId employee ID to search
     * @return EmployeeDTO of given employeeId
     */
    @GetMapping("/view/{employeeId}")
    public String viewEmployeeById(@PathVariable Long employeeId, Model model) {
        final Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            return "redirect:/employees/list";
        }
        EmployeeDTO employeeDTO = EmployeeMapper.toEmployeeDTO(employee);
        model.addAttribute("employee", employeeDTO);
        return "view-employee";
    }

    /**
     * Updates existing employee with given employee data
     * Retrieves the employee by ID and prefills the same form (used for add)
     * @param employeeId employeeId which needs to be updated
     * @param model new employee data
     * @return add-employee jsp view to edit the employee
     */
    @GetMapping("/edit/{employeeId}")
    public String showEditEmployeeForm(@PathVariable Long employeeId, Model model) {
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        if (existingEmployee == null) {
            return "redirect:/employees/list";
        }
        model.addAttribute("employee", existingEmployee);
        model.addAttribute("departments", departments);
        model.addAttribute("managers", employeeService.getAllManagers());
        return "add-employee";
    }


    /**
     * Delete Employee
     * @param employeeId employee ID to delete
     * @return deletes the employee and redirects to list-employees view
     */
    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/employees/list";
    }
}
