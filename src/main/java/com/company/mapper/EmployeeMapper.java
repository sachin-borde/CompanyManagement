package com.company.mapper;

import com.company.dtos.EmployeeDto;
import com.company.entity.Employee;
import com.company.entity.Project;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeDto dto, Project project) {

        Employee employee = new Employee();

        employee.setName(dto.getName());
        employee.setSalary(dto.getSalary());
        employee.setProject(project);

        return employee;
    }

    public static EmployeeDto toDto(Employee employee) {

        EmployeeDto dto = new EmployeeDto();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());
        dto.setProjectId(employee.getProject().getId());

        return dto;

    }

}
