package com.company.mapper;

import com.company.dtos.CompanyDto;
import com.company.entity.Company;

public class CompanyMapper {

    public static Company toEntity(CompanyDto dto) {

        Company company = new Company();

        company.setName(dto.getName());
        company.setLocation(dto.getLocation());

        return company;
    }

    public static CompanyDto toDto(Company company) {

        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        companyDto.setLocation(company.getLocation());

        return companyDto;

    }

}
