package by.koronatech.office.core.service;


import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.api.exeption.EmployeeAlreadyManagerException;
import by.koronatech.office.core.repository.DepartmentRepository;
import by.koronatech.office.core.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public void addEmployee(EmployeeDto employeeDto) {
        departmentRepository.findByName(employeeDto.getDepartment())
                .orElseThrow(() -> new RuntimeException("Отдел не найден: " + employeeDto.getDepartment()));

        employeeRepository.save(employeeDto);
    }

    public List<EmployeeDto> getEmployeesByDepartment(String department, int page, int size) {
        return employeeRepository.findAllByDepartment(department, page, size);
    }

    public void promoteToManager(Integer employeeId) {
        EmployeeDto employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден: " + employeeId));

        if (Boolean.TRUE.equals(employee.getManager())) {
            throw new EmployeeAlreadyManagerException("Сотрудник уже является менеджером: " + employee.getName());
        }

        employee.setManager(true);
        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        if (!employeeRepository.findById(employeeDto.getId()).isPresent()) {
            throw new RuntimeException("Сотрудник не найде: " + employeeDto.getId());
        }
        employeeRepository.save(employeeDto);
    }

    public void deleteEmployee(Integer employeeId) {
        if (!employeeRepository.findById(employeeId).isPresent()) {
            throw new RuntimeException("Сотрудник не найден: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
    }

}
