package by.koronatech.office.api.controller;

import by.koronatech.office.api.dto.DepartmentDto;
import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private static final String DEFAULT_PAGE_VALUE = "0";
    private static final String DEFAULT_SIZE_VALUE = "5";

    @GetMapping
    public List<Department> getAllDepartments(@RequestParam(defaultValue = DEFAULT_PAGE_VALUE) int page,
                                              @RequestParam(defaultValue = DEFAULT_SIZE_VALUE) int size) {
        return departmentService.getAllDepartments(page, size);
    }
}
