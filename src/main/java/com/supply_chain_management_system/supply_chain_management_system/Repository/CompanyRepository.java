package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findByCompanyNameContainingIgnoreCase(
            String companyName,
            Pageable pageable
    );
}