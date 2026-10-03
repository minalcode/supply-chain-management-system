package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Company;
import com.supply_chain_management_system.supply_chain_management_system.Repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Company getCompanyById(Long id) {

        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));
    }

    public Company updateCompany(
            Long id,
            Company company) {

        Company existingCompany =
                getCompanyById(id);

        existingCompany.setCompanyName(
                company.getCompanyName()
        );

        existingCompany.setRegistrationNumber(
                company.getRegistrationNumber()
        );

        existingCompany.setEmail(
                company.getEmail()
        );

        existingCompany.setPhone(
                company.getPhone()
        );

        existingCompany.setAddress(
                company.getAddress()
        );

        existingCompany.setCity(
                company.getCity()
        );

        existingCompany.setState(
                company.getState()
        );

        existingCompany.setCountry(
                company.getCountry()
        );

        existingCompany.setStatus(
                company.getStatus()
        );

        return companyRepository.save(existingCompany);
    }

    public void deleteCompany(Long id) {

        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found");
        }

        companyRepository.deleteById(id);
    }

    // Pagination
    public Page<Company> getCompanies(
            Pageable pageable) {

        return companyRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<Company> searchCompanies(
            String keyword,
            Pageable pageable) {

        return companyRepository
                .findByCompanyNameContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }
}