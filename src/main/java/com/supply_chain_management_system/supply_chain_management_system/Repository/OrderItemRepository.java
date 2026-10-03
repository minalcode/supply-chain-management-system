package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

    @Query("""
            SELECT oi FROM OrderItem oi
            WHERE LOWER(oi.product.productName)
            LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(oi.purchaseOrder.orderNumber)
            LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<OrderItem> searchOrderItems(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}