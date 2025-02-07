package by.koronatech.office.ControllerTest;

import by.koronatech.office.api.controller.DepartmentController;
import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllDepartments() throws Exception {

        Department department = new Department();
        department.setId(1);
        department.setName("HR");


        when(departmentService.getAllDepartments(0, 5)).thenReturn(Collections.singletonList(department));


        mockMvc.perform(get("/departments")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("HR"));
    }

    @Test
    public void testGetAllDepartmentsWithDefaults() throws Exception {

        Department department = new Department();
        department.setId(1);
        department.setName("HR");


        when(departmentService.getAllDepartments(0, 5)).thenReturn(Collections.singletonList(department));


        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("HR"));
    }
}
