package by.koronatech.office.api.controller;


import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.core.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public void addEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.addEmployee(employeeDto);
    }

    @GetMapping("/{department}")
    public List<EmployeeDto> getEmployeesByDepartment(
            @PathVariable String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return employeeService.getEmployeesByDepartment(department, page, size);
    }

    @PutMapping("/{id}/promote")
    public void promoteToManager(@PathVariable Integer id) {
        employeeService.promoteToManager(id);
    }

    @PutMapping
    public void updateEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

}
