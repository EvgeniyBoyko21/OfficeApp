package by.koronatech.office.core.repository;


import by.koronatech.office.core.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByDepartmentName(String departmentName);
}
