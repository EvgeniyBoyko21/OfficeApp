package by.koronatech.office.ControllerTest;

import by.koronatech.office.api.controller.EmployeeController;
import by.koronatech.office.api.dto.EmployeeCreateDto;
import by.koronatech.office.api.dto.EmployeeDto;
import by.koronatech.office.core.entity.Employee;
import by.koronatech.office.core.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @Test
    public void testAddEmployee() throws Exception {
        // Подготовка данных для теста
        EmployeeCreateDto employeeCreateDto = new EmployeeCreateDto();
        employeeCreateDto.setName("John Doe");
        employeeCreateDto.setSalary(5000);
        employeeCreateDto.setDepartment("HR");
        employeeCreateDto.setManager(false);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("John Doe");
        employeeDto.setSalary(5000);
        employeeDto.setDepartment("HR");
        employeeDto.setManager(false);


        when(employeeService.addEmployee(employeeCreateDto)).thenReturn(employeeDto);


        mockMvc.perform(post("/employees")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\", \"salary\":5000, \"department\":\"HR\", \"manager\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetEmployeesByDepartment() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setSalary(5000);
        employee.setDepartment("HR");
        employee.setManager(false);


        when(employeeService.getEmployeesByDepartment("HR", 0, 5)).thenReturn(Collections.singletonList(employee));


        mockMvc.perform(get("/employees/HR")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testPromoteToManager() throws Exception {

        mockMvc.perform(patch("/employees/1/promote"))
                .andExpect(status().isOk());

        verify(employeeService).promoteToManager(1);
    }

    @Test
    public void testUpdateEmployee() throws Exception {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setName("John Doe");
        employeeDto.setSalary(5000);
        employeeDto.setDepartment("HR");
        employeeDto.setManager(false);


        when(employeeService.updateEmployee(employeeDto)).thenReturn(employeeDto);


        mockMvc.perform(put("/employees/1")
                        .contentType("application/json")
                        .content("{\"name\":\"John Doe\", \"salary\":5000, \"department\":\"HR\", \"manager\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployee() throws Exception {

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());


        verify(employeeService).deleteEmployee(1);
    }
}
