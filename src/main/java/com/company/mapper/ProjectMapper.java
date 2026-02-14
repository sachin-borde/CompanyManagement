package com.company.mapper;

import com.company.dtos.ProjectDto;
import com.company.entity.Department;
import com.company.entity.Project;

public class ProjectMapper {

    public static Project toEntity(ProjectDto dto, Department department){

        Project project=new Project();

        project.setName(dto.getName());
        project.setBudget(dto.getBudget());
        project.setDepartment(department);

        return project;

    }

    public static ProjectDto toDto(Project project){

        ProjectDto dto=new ProjectDto();

        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setBudget(project.getBudget());
        dto.setDepartmentId(project.getDepartment().getId());

        return dto;

    }

}

