package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository
        extends JpaRepository<Shipment, Long> {

    Page<Shipment> findByTrackingNumberContainingIgnoreCase(
            String trackingNumber,
            Pageable pageable
    );
}