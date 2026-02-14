package com.company.controller;

import com.company.dtos.EmployeeDto;
import com.company.entity.Employee;
import com.company.entity.Project;
import com.company.mapper.EmployeeMapper;
import com.company.payload.ApiResponse;
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
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public EmployeeController(EmployeeService employeeService,
                              ProjectService projectService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(@PathVariable Long projectId,
                                                                   @Valid @RequestBody EmployeeDto dto) {

        Project project = projectService.getProjectById(projectId);

        Employee employee = EmployeeMapper.toEntity(dto, project);

        Employee saved = employeeService.createEmployee(projectId, employee);

        ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Employee Created Successfully", EmployeeMapper.toDto(employee));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees() {

        List<EmployeeDto> employees = employeeService.getAllEmployees()
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();

        ApiResponse<List<EmployeeDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Employee Fetched Successfully", employees);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllEmployeesPaginated(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        Page<Employee> employeePage = employeeService.getAllEmployeesPaginated(pageable);

        List<EmployeeDto> employees = employeePage
                .getContent()
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", employees);
        responseData.put("currentPage", employeePage.getNumber());
        responseData.put("totalItems", employeePage.getTotalElements());
        responseData.put("totalPages", employeePage.getTotalPages());
        responseData.put("pageSize", employeePage.getSize());
        responseData.put("last", employeePage.isLast());

        ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Employee Fetched Successfully",
                responseData
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long id) {

        Employee employee = employeeService.getEmployeeById(id);

        ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Employee Fetch successfully", EmployeeMapper.toDto(employee));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(@PathVariable Long id,
                                                                   @Valid @RequestBody EmployeeDto dto) {

        Employee exist = employeeService.getEmployeeById(id);

        exist.setName(dto.getName());
        exist.setSalary(dto.getSalary());

        Employee updated = employeeService.updateEmployee(id, exist);

        ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data Updated successfully", EmployeeMapper.toDto(updated));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> deleteEmployee(@PathVariable Long id) {

        Employee employee = employeeService.deleteEmployee(id);

        ApiResponse<EmployeeDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data Deleted Successfully", EmployeeMapper.toDto(employee));

        return ResponseEntity.ok(response);
    }

}
