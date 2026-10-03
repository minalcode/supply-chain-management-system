package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.ProcurementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcurementRequestRepository
        extends JpaRepository<ProcurementRequest, Long> {

    Page<ProcurementRequest> findByRequestNumberContainingIgnoreCase(
            String requestNumber,
            Pageable pageable
    );
}