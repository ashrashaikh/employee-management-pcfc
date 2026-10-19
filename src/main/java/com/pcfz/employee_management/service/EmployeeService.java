package com.pcfz.employee_management.service;

import com.pcfz.employee_management.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.pcfz.employee_management.repository.EmployeeRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Path UPLOAD_DIR =
            Paths.get("uploads", "employees").toAbsolutePath().normalize();

    private static final Set<String> ALLOWED_FILE_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".gif", ".webp");

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
        // Resolve managerID to the managed Employee entity.
        if (employee.getManager() != null && employee.getManager().getId() != null) {
            Employee manager = getEmployeeById(employee.getManager().getId());
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
        validateEmployeeAge(employee.getDob());
        return employeeRepository.save(employee);
    }

    /**
     * saveEmployee with profile photo - for the JSP upload flow.
     * @param employee Employee data to be added and saved to db
     * @param profilePhotoFile Employee profile photo
     * @return updated employee details
     */
    @Transactional
    public Employee saveEmployee(final Employee employee,
                                 final MultipartFile profilePhotoFile) {

        // Preserve the existing photo when editing without a replacement.
        if (employee.getId() != null) {
            Employee existingEmployee = getEmployeeById(employee.getId());
            employee.setProfilePhotoPath(existingEmployee.getProfilePhotoPath());
        }

        // Save a new photo only when a file was selected.
        if (profilePhotoFile != null && !profilePhotoFile.isEmpty()) {
            employee.setProfilePhotoPath(saveProfilePhoto(profilePhotoFile));
        }

        // Resolve managerID to the managed Employee entity.
        if (employee.getManager() != null
                && employee.getManager().getId() != null) {
            Employee manager = getEmployeeById(employee.getManager().getId());
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
        validateEmployeeAge(employee.getDob());
        return employeeRepository.save(employee);
    }

    /**
     * Employee Age Validation
     * 1. Age should not be null
     * 2. Age should not be less than 18 years
     * @param dob employee date of birth
     */
    private void validateEmployeeAge(LocalDate dob) {
        if (Objects.isNull(dob)) {
            throw new RuntimeException("DOB is required.");
        }
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new RuntimeException("Employee age should not be less than 18 years.");
        }
    }

    /**
     * Save/Update Employee Profile Photo
     * @param profilePhoto uploaded profile photo file
     * @return profile photo path
     */
    private String saveProfilePhoto(MultipartFile profilePhoto) {
        if (Objects.isNull(profilePhoto) || profilePhoto.isEmpty()) {
            return null;
        }

        // 1. Get the original filename (e.g., "avatar.png")
        String originalFilename = profilePhoto.getOriginalFilename();

        if (Objects.isNull(originalFilename) || originalFilename.isBlank()) {
            throw new RuntimeException("Invalid photo filename.");
        }
        // 2.Check file type extension and allow only the allowed types to be uploaded.
        String filename = Paths.get(originalFilename).getFileName().toString();
        int dot = filename.lastIndexOf('.');

        if (dot <= 0) {
            throw new RuntimeException("Photo must be a JPG, PNG, GIF, or WEBP image.");
        }

        String extension = filename.substring(dot).toLowerCase();

        if (!ALLOWED_FILE_EXTENSIONS.contains(extension)) {
            throw new RuntimeException("Only JPG, PNG, GIF, and WEBP images are allowed.");
        }

        // 3. Maximum upload size: 5 MB
        if (profilePhoto.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Photo size must not exceed 5 MB.");
        }

        try {
            Files.createDirectories(UPLOAD_DIR);
            String uniqueFilename = UUID.randomUUID() + extension;
            Path destination = UPLOAD_DIR.resolve(uniqueFilename).normalize();
            if (!destination.startsWith(UPLOAD_DIR)) {
                throw new RuntimeException("Invalid upload path.");
            }

            Files.copy(profilePhoto.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/employees/" + uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save employee photo.", e);
        }
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
        if (employee.getProfilePhotoPath() != null
                && !employee.getProfilePhotoPath().isBlank()) {
            existingEmployee.setProfilePhotoPath(employee.getProfilePhotoPath());
        }

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
