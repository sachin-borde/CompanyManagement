package com.company.controller;

import com.company.dtos.DepartmentDto;
import com.company.entity.Company;
import com.company.entity.Department;
import com.company.mapper.DepartmentMapper;
import com.company.payload.ApiResponse;
import com.company.service.CompanyService;
import com.company.service.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final CompanyService companyService;

    public DepartmentController(DepartmentService departmentService,
                                CompanyService companyService) {
        this.departmentService = departmentService;
        this.companyService = companyService;
    }

    @PostMapping("/{companyId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@PathVariable Long companyId,
                                                                       @Valid @RequestBody DepartmentDto dto) {

        Company company = companyService.getCompanyById(companyId);
        Department department = DepartmentMapper.toEntity(dto, company);

        Department saved = departmentService.createDepartment(companyId, department);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Department saved succesfully", DepartmentMapper.toDto(saved));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments() {

        List<DepartmentDto> departments = departmentService.getAllDepartments()
                .stream()
                .map(DepartmentMapper::toDto)
                .toList();

        ApiResponse<List<DepartmentDto>> response =
                new ApiResponse<>(HttpStatus.OK.value(), "Department Fetched Successfully", departments);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(@PathVariable Long id) {

        Department department = departmentService.getDepartmentById(id);

        ApiResponse<DepartmentDto> response =
                new ApiResponse<>(HttpStatus.OK.value(), "Department Fetch successfully", DepartmentMapper.toDto(department));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(@PathVariable Long id,
                                                                       @Valid @RequestBody DepartmentDto dto) {

        Department department = departmentService.getDepartmentById(id);
        department.setName(dto.getName());

        Department updated = departmentService.updateDepartment(id, department);

        ApiResponse<DepartmentDto> response =
                new ApiResponse<>(HttpStatus.OK.value(), "Data Updated Successfully", DepartmentMapper.toDto(updated));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(@PathVariable Long id) {

        Department department = departmentService.deleteDepartment(id);

        ApiResponse<DepartmentDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data deleted Successfully", DepartmentMapper.toDto(department));

        return ResponseEntity.ok(response);
    }

}
