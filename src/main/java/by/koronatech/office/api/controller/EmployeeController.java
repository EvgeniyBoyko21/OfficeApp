package by.koronatech.office.api.controller;


import by.koronatech.office.api.dto.EmployeeCreateDto;
import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.core.entity.Employee;
import by.koronatech.office.core.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private static final String DEFAULT_PAGE_VALUE = "0";
    private static final String DEFAULT_SIZE_VALUE = "5";



    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeCreateDto employeeCreateDto) {
        EmployeeDto createdEmployee = employeeService.addEmployee(employeeCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @GetMapping("/{department}")
    public List<Employee> getEmployeesByDepartment(
            @PathVariable String department,
            @RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size) {
        return employeeService.getEmployeesByDepartment(department, page, size);
    }

    @PatchMapping("/{id}/promote")
    public ResponseEntity<Void> promoteToManager(@PathVariable Integer id) {
        employeeService.promoteToManager(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateEmployee(
            @PathVariable int id,
            @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

}
