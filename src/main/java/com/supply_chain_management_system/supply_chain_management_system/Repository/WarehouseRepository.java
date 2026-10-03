package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository
        extends JpaRepository<Warehouse, Long> {

    Page<Warehouse>
    findByWarehouseNameContainingIgnoreCaseOrWarehouseCodeContainingIgnoreCase(
            String warehouseName,
            String warehouseCode,
            Pageable pageable
    );
}