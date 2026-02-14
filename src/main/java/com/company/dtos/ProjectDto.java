package com.company.dtos;

import com.company.entity.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProjectDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Double budget;

    private Long departmentId;

}
