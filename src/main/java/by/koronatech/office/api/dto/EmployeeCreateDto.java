package by.koronatech.office.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeCreateDto {
    private String name;
    private BigDecimal salary;
    private String department;
    private Boolean manager;
}
