package com.company.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentDto {

    private Long id;

    @NotBlank
    private String name;

    private Long companyId;

}
