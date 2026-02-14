package com.company.service;

import com.company.entity.Company;
import com.company.exception.ResourceNotFoundException;
import com.company.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id){
        return companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Company not found with id: " + id));
    }

    public Company updateCompany(Long id, Company company){
        Company existing = companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Company not found with id: " + id));

        existing.setName(company.getName());
        existing.setLocation(company.getLocation());

        return companyRepository.save(existing);
    }

    public Company deleteCompany(Long id){
        Company company = companyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Company not found with id: " + id));

        companyRepository.delete(company);

        return company;
    }
}
