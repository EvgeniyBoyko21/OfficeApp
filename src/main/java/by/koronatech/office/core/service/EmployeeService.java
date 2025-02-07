package by.koronatech.office.core.service;


import by.koronatech.office.api.dto.EmployeeCreateDto;
import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.core.entity.Employee;
import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.repository.DepartmentRepository;
import by.koronatech.office.core.repository.EmployeeRepository;
import by.koronatech.office.api.exeption.EmployeeAlreadyManagerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeDto addEmployee(EmployeeCreateDto employeeCreateDto) {
        Department department = departmentRepository.findByName(employeeCreateDto.getDepartment())
                .orElseThrow(() -> new RuntimeException("Отдел не найден: " + employeeCreateDto.getDepartment()));
        Employee employee = new Employee();
        employee.setName(employeeCreateDto.getName());
        employee.setSalary(employeeCreateDto.getSalary());
        employee.setDepartment(department);
        employee.setManager(employeeCreateDto.getManager());

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeDto(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getSalary(),
                employee.getDepartment().getName(), savedEmployee.getManager());
    }

    public List<Employee> getEmployeesByDepartment(String departmentName, int page, int size) {
        return employeeRepository.findAllByDepartmentName(departmentName);
    }

    public void promoteToManager(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден: " + employeeId));

        if (Boolean.TRUE.equals(employee.getManager())) {
            throw new EmployeeAlreadyManagerException("Сотрудник уже является менеджером: " + employee.getName());
        }

        employee.setManager(true);
        employeeRepository.save(employee);
    }

    public void updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден: " + employeeDto.getId()));

        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        Department department = departmentRepository.findByName(employeeDto.getDepartment())
                .orElseThrow(() -> new RuntimeException("Отдел не найден: " + employeeDto.getDepartment()));
        employee.setDepartment(department);
        employee.setManager(employeeDto.getManager());
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }
}
