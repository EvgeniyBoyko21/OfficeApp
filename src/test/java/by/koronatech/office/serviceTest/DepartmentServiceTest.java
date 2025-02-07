package by.koronatech.office.serviceTest;

import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.repository.DepartmentRepository;
import by.koronatech.office.core.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    public void testGetAllDepartments() {

        Department department = new Department();
        department.setName("HR");

        when(departmentRepository.findAll()).thenReturn(List.of(department));


        List<Department> departments = departmentService.getAllDepartments(0, 5);


        assertNotNull(departments);
        assertEquals(1, departments.size());
        assertEquals("HR", departments.get(0).getName());
    }

    @Test
    public void testGetDepartmentByName() {

        Department department = new Department();
        department.setName("HR");

        when(departmentRepository.findByName("HR")).thenReturn(Optional.of(department));


        Department result = departmentService.getDepartmentByName("HR");


        assertNotNull(result);
        assertEquals("HR", result.getName());
    }

    @Test
    public void testGetDepartmentByNameNotFound() {

        when(departmentRepository.findByName("Finance")).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> departmentService.getDepartmentByName("Finance"));
        assertEquals("Отдел не найден: Finance", exception.getMessage());
    }
}
