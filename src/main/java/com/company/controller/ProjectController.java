package com.company.controller;

import com.company.dtos.ProjectDto;
import com.company.entity.Department;
import com.company.entity.Project;
import com.company.mapper.ProjectMapper;
import com.company.payload.ApiResponse;
import com.company.service.DepartmentService;
import com.company.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final DepartmentService departmentService;

    public ProjectController(ProjectService projectService,
                             DepartmentService departmentService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
    }

    @PostMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<ProjectDto>> createProject(@PathVariable Long departmentId,
                                                                 @Valid @RequestBody ProjectDto dto) {

        Department department = departmentService.getDepartmentById(departmentId);

        Project project = ProjectMapper.toEntity(dto, department);

        Project saved = projectService.createProject(departmentId, project);

        ApiResponse<ProjectDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Project Saved successfully", ProjectMapper.toDto(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectDto>>> getAllProjects() {

        List<ProjectDto> projects = projectService.getAllProjects()
                .stream()
                .map(ProjectMapper::toDto)
                .toList();

        ApiResponse<List<ProjectDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Projects Fetched Successfully", projects);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDto>> getProjectById(@PathVariable Long id) {

        Project project = projectService.getProjectById(id);

        ApiResponse<ProjectDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Project Fetch Successfully", ProjectMapper.toDto(project));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDto>> updateProject(@PathVariable Long id,
                                                                 @Valid @RequestBody ProjectDto dto) {

        Project exist = projectService.getProjectById(id);

        exist.setName(dto.getName());
        exist.setBudget(dto.getBudget());

        Project updated = projectService.updateProject(id, exist);

        ApiResponse<ProjectDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data updated successfully", ProjectMapper.toDto(updated));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectDto>> deleteProject(@PathVariable Long id) {

        Project project = projectService.deleteProject(id);

        ApiResponse<ProjectDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data deleted Successfully", ProjectMapper.toDto(project));

        return ResponseEntity.ok(response);
    }

}
