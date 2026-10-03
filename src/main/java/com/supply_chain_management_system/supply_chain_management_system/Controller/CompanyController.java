package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.CompanyDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Company;
import com.supply_chain_management_system.supply_chain_management_system.Service.CompanyService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO dto) {
        Company company = modelMapper.map(dto, Company.class);
        Company saved = companyService.createCompany(company);
        return ResponseEntity.ok(modelMapper.map(saved, CompanyDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies()
                .stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .toList();

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(
                modelMapper.map(
                        companyService.getCompanyById(id),
                        CompanyDTO.class
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyDTO dto) {

        Company company = modelMapper.map(dto, Company.class);
        Company updated = companyService.updateCompany(id, company);

        return ResponseEntity.ok(
                modelMapper.map(updated, CompanyDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("Company deleted successfully");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CompanyDTO>> getCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CompanyDTO> companies = companyService
                .getCompanies(pageable)
                .map(company -> modelMapper.map(company, CompanyDTO.class));

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDTO>> searchCompanies(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CompanyDTO> companies = companyService
                .searchCompanies(keyword, pageable)
                .map(company -> modelMapper.map(company, CompanyDTO.class));

        return ResponseEntity.ok(companies);
    }
}