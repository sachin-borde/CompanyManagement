package com.company.controller;

import com.company.dtos.CompanyDto;
import com.company.entity.Company;
import com.company.mapper.CompanyMapper;
import com.company.payload.ApiResponse;
import com.company.service.CompanyService;
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
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyDto>> createCompany(
            @Valid @RequestBody CompanyDto companyDto) {

        Company company = CompanyMapper.toEntity(companyDto);

        Company saved = companyService.createCompany(company);

        CompanyDto dto = CompanyMapper.toDto(saved);

        ApiResponse<CompanyDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Company Created Successfully", dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyDto>>> getAllCompanies() {

        List<CompanyDto> companies = companyService.getAllCompanies()
                .stream()
                .map(CompanyMapper::toDto)
                .toList();

        ApiResponse<List<CompanyDto>> response = new ApiResponse<>(HttpStatus.OK.value(), "Company Fetched Successfully", companies);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllCompanyPaginated(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));

        Page<Company> companyPage = companyService.getAllCompanyPaginated(pageable);

        List<CompanyDto> companies = companyPage
                .getContent()
                .stream()
                .map(CompanyMapper::toDto)
                .toList();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("content", companies);
        responseData.put("currentPage", companyPage.getNumber());
        responseData.put("totalItems", companyPage.getTotalElements());
        responseData.put("totalPages", companyPage.getTotalPages());
        responseData.put("pageSize", companyPage.getSize());
        responseData.put("last", companyPage.isLast());

        ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Employee Fetched Successfully",
                responseData
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> getCompanyById(@PathVariable Long id) {

        Company company = companyService.getCompanyById(id);

        ApiResponse<CompanyDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Company Fetch successfully", CompanyMapper.toDto(company));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> updateCompany(@PathVariable Long id,
                                                                 @Valid @RequestBody CompanyDto dto) {

        Company updated = companyService.updateCompany(id, CompanyMapper.toEntity(dto));

        ApiResponse<CompanyDto> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Data Updated Successfully", CompanyMapper.toDto(updated));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> deleteCompany(@PathVariable Long id) {

        Company company = companyService.deleteCompany(id);

        ApiResponse<CompanyDto> response = new ApiResponse<>(HttpStatus.OK.value(), "Data Deleted Successfully", CompanyMapper.toDto(company));

        return ResponseEntity.ok(response);
    }

}
