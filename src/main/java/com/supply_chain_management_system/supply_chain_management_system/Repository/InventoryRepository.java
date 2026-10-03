package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    @Query("""
            SELECT i FROM Inventory i
            WHERE LOWER(i.product.productName)
            LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(i.warehouse.warehouseName)
            LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Inventory> searchInventory(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}