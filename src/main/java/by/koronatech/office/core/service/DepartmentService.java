package by.koronatech.office.core.service;


import by.koronatech.office.api.dto.DepartmentDto;
import by.koronatech.office.core.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public DepartmentDto getDepartmentByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Отдел не найден:" + name));
    }
}
