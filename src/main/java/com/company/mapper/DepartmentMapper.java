package com.company.mapper;

import com.company.dtos.DepartmentDto;
import com.company.entity.Company;
import com.company.entity.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentDto dto, Company company) {

        Department department = new Department();

        department.setName(dto.getName());
        department.setCompany(company);

        return department;

    }

    public static DepartmentDto toDto(Department department) {

        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setCompanyId(department.getCompany().getId());

        return departmentDto;

    }

}
