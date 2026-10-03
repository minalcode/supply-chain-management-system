package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    Page<PurchaseOrder> findByOrderNumberContainingIgnoreCase(
            String orderNumber,
            Pageable pageable
    );
}