package com.supply_chain_management_system.supply_chain_management_system.Repository;

import com.supply_chain_management_system.supply_chain_management_system.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContainingIgnoreCase(
            String productName,
            Pageable pageable
    );

    @Query("""
            SELECT p FROM Product p
            WHERE (:category IS NULL OR LOWER(p.category) = LOWER(:category))
            AND (:status IS NULL OR LOWER(p.status) = LOWER(:status))
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            """)
    Page<Product> filterProducts(
            @Param("category") String category,
            @Param("status") String status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}