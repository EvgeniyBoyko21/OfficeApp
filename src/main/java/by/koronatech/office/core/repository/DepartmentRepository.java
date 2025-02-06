package by.koronatech.office.core.repository;

import by.koronatech.office.api.dto.DepartmentDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class DepartmentRepository {
    private final Map<Integer, DepartmentDto> departments = new HashMap<>();

    public List<DepartmentDto> findAll() {
        return new ArrayList<>(departments.values());
    }

    public Optional<DepartmentDto> findById(Integer id) {
        return Optional.ofNullable(departments.get(id));
    }

    public Optional<DepartmentDto> findByName(String name) {
        return departments.values().stream()
                .filter(d -> d.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @PostConstruct
    private void init() {
        departments.put(1, new DepartmentDto() {{setId(1); setName("Бухгалтерия"); }});
        departments.put(2, new DepartmentDto() {{setId(2); setName("Продажи"); }});
        departments.put(3, new DepartmentDto() {{setId(3); setName("Маркетинг"); }});
    }
}
