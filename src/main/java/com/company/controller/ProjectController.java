package com.company.controller;

import com.company.dtos.ProjectDto;
import com.company.entity.Department;
import com.company.entity.Project;
import com.company.mapper.ProjectMapper;
import com.company.payload.ApiResponse;
import com.company.service.DepartmentService;
import com.company.service.EmployeeService;
import com.company.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService,
                             DepartmentService departmentService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
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

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllProjectsPaginated(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        Page<Project> projectPage = projectService.getAllProjectsPaginated(pageable);

        List<ProjectDto> projects = projectPage
                .getContent()
                .stream()
                .map(ProjectMapper::toDto)
                .toList();

        Map<String, Object> responseData = new HashMap<>();

        responseData.put("content", projects);
        responseData.put("currentPage", projectPage.getNumber());
        responseData.put("totalItems", projectPage.getTotalElements());
        responseData.put("totalPages", projectPage.getTotalPages());
        responseData.put("pageSize", projectPage.getSize());
        responseData.put("last", projectPage.isLast());

        ApiResponse<Map<String, Object>> response = new ApiResponse<>(HttpStatus.OK.value(), "Project Retried successfully", responseData);

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
