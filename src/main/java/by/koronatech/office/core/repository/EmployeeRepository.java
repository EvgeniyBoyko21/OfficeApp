package by.koronatech.office.core.repository;


import by.koronatech.office.api.dto.EmployeeDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final Map<Integer, EmployeeDto> employees = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);


    public List<EmployeeDto> findAllByDepartment(String department, int page, int size) {
        return employees.values().stream()
                .filter(e->e.getDepartment().equalsIgnoreCase(department))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public void save(EmployeeDto employeeDto) {
        if (employeeDto.getId()==null) {
            employeeDto.setId(idGenerator.getAndIncrement());
        }
        employees.put(employeeDto.getId(), employeeDto);
    }

    public Optional<EmployeeDto> findById(Integer id) {
        return  Optional.ofNullable(employees.get(id));
    }

    public void deleteById(Integer id) {
        employees.remove(id);
    }
}
