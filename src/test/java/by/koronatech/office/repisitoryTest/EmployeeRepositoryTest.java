package by.koronatech.office.repisitoryTest;


import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.entity.Employee;
import by.koronatech.office.core.repository.DepartmentRepository;
import by.koronatech.office.core.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testSaveAndFindById() {
        Department department = departmentRepository.save(new Department(null, "IT"));
        Employee employee = new Employee(null, "John Doe", "Developer", department);
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> found = employeeRepository.findById(savedEmployee.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAllWithPagination() {
        Department department = departmentRepository.save(new Department(null, "HR"));
        employeeRepository.save(new Employee(null, "Alice", "HR Manager", department));
        employeeRepository.save(new Employee(null, "Bob", "HR Assistant", department));

        Pageable pageable = PageRequest.of(0, 1);
        Page<Employee> page = employeeRepository.findAll(pageable);

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isGreaterThan(1);
    }

    @Test
    void testFindByDepartmentId() {
        Department department = departmentRepository.save(new Department(null, "Finance"));
        Employee employee = employeeRepository.save(new Employee(null, "Charlie", "Accountant", department));

        var employees = employeeRepository.findAllByDepartmentName(department.getName());
        assertThat(Optional.ofNullable(employees)).contains(employee);
    }

    @Test
    void testDeleteById() {
        Department department = departmentRepository.save(new Department(null, "Marketing"));
        Employee employee = employeeRepository.save(new Employee(null, "David", "Marketing Manager", department));

        employeeRepository.deleteById(employee.getId());
        Optional<Employee> found = employeeRepository.findById(employee.getId());
        assertThat(found).isEmpty();
    }
}
