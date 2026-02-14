package com.company.dtos;

import com.company.entity.Company;
import com.company.entity.Project;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentDto {

    private Long id;

    @NotBlank
    private String name;

    private Long companyId;

}
