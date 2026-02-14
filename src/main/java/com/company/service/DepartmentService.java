package com.company.service;

import com.company.entity.Company;
import com.company.entity.Department;
import com.company.exception.ResourceNotFoundException;
import com.company.repository.CompanyRepository;
import com.company.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    public Department createDepartment(Long companyId, Department department){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->new ResourceNotFoundException("Company not found with id: " + companyId));

        department.setCompany(company);

        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id){
        return departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department updateDepartment(Long id, Department department){
        Department existing = departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + id));

        existing.setName(department.getName());

        return departmentRepository.save(existing);
    }

    public Department deleteDepartment(Long id){
        Department department = departmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Department not found with id: " + id));

        departmentRepository.delete(department);

        return department;
    }

}
