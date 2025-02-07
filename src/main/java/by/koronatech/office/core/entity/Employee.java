package by.koronatech.office.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private Boolean manager = false;
}
