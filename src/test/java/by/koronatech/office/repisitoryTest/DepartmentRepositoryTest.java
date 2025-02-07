package by.koronatech.office.repisitoryTest;

import by.koronatech.office.core.entity.Department;
import by.koronatech.office.core.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldSaveAndFindDepartmentByName() {
        Department department = new Department();
        department.setName("IT");
        Department savedDepartment = departmentRepository.save(department);

        Optional<Department> found = departmentRepository.findByName("IT");
        assertThat(found).isPresent()
                .contains(savedDepartment);
    }
}


