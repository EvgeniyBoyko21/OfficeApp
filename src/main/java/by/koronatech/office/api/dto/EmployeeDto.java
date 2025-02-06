package by.koronatech.office.api.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Integer id;
    private String name;
    private Double salary;
    private String department;
    private Boolean manager;
}
