package by.koronatech.office.serviceTest;

import by.koronatech.office.api.dto.EmployeeCreateDto;
import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.entity.Employee;
import by.koronatech.office.core.repository.DepartmentRepository;
import by.koronatech.office.core.repository.EmployeeRepository;
import by.koronatech.office.api.exeption.EmployeeAlreadyManagerException;
import by.koronatech.office.core.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testAddEmployee() {
        Department department = new Department();
        department.setName("HR");

        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));

        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto("John Doe", 50000, "HR", false);


        Employee savedEmployee = new Employee();
        savedEmployee.setId(1);
        savedEmployee.setName("John Doe");
        savedEmployee.setSalary(50000);
        savedEmployee.setDepartment(department);
        savedEmployee.setManager(false);

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);


        EmployeeDto result = employeeService.addEmployee(employeeCreateDto);


        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals(50000, result.getSalary());
        assertEquals("HR", result.getDepartment());
        assertFalse(result.getManager());
    }

    @Test
    public void testPromoteToManager() {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setManager(false);


        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        employeeService.promoteToManager(1);


        assertTrue(employee.getManager());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testPromoteToManagerAlreadyManager() {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setManager(true);


        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));


        EmployeeAlreadyManagerException exception = assertThrows(EmployeeAlreadyManagerException.class, () -> employeeService.promoteToManager(1));
        assertEquals("Сотрудник уже является менеджером: John Doe", exception.getMessage());
    }

    @Test
    public void testUpdateEmployee() {

        Department department = new Department();
        department.setName("HR");

        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));


        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setSalary(50000);
        employee.setDepartment(department);
        employee.setManager(false);


        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeDto employeeDto = new EmployeeDto(1, "John Doe", 55000, "HR", false);


        employeeService.updateEmployee(employeeDto);


        assertEquals(55000, employee.getSalary());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");


        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));


        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteEmployeeNotFound() {

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(1));
        assertEquals("Сотрудник не найден: 1", exception.getMessage());
    }
}
