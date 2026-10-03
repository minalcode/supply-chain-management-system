package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Inventory;
import com.supply_chain_management_system.supply_chain_management_system.Model.Product;
import com.supply_chain_management_system.supply_chain_management_system.Model.Warehouse;
import com.supply_chain_management_system.supply_chain_management_system.Repository.InventoryRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.ProductRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {

        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    // CREATE INVENTORY
    public Inventory createInventory(
            Inventory inventory,
            Long productId,
            Long warehouseId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                ));

        Warehouse warehouse =
                warehouseRepository.findById(warehouseId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Warehouse not found"
                                ));

        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);

        if (inventory.getAvailableQuantity() == null) {
            inventory.setAvailableQuantity(0);
        }

        if (inventory.getReservedQuantity() == null) {
            inventory.setReservedQuantity(0);
        }

        if (inventory.getReorderLevel() == null) {
            inventory.setReorderLevel(10);
        }

        updateStockStatus(inventory);

        return inventoryRepository.save(inventory);
    }

    // GET ALL
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // GET BY ID
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Inventory not found"
                        ));
    }

    // DELETE
    public void deleteInventory(Long id) {

        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException(
                    "Inventory not found"
            );
        }

        inventoryRepository.deleteById(id);
    }

    // PAGINATION
    public Page<Inventory> getInventory(Pageable pageable) {
        return inventoryRepository.findAll(pageable);
    }

    // SEARCH
    public Page<Inventory> searchInventory(
            String keyword,
            Pageable pageable) {

        return inventoryRepository.searchInventory(
                keyword,
                pageable
        );
    }

    // STOCK INCREASE
    @Transactional
    public Inventory addStock(
            Long inventoryId,
            Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0"
            );
        }

        Inventory inventory =
                getInventoryById(inventoryId);

        int currentStock =
                inventory.getAvailableQuantity() == null
                        ? 0
                        : inventory.getAvailableQuantity();

        inventory.setAvailableQuantity(
                currentStock + quantity
        );

        updateStockStatus(inventory);

        return inventoryRepository.save(inventory);
    }

    // STOCK DECREASE
    @Transactional
    public Inventory removeStock(
            Long inventoryId,
            Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than 0"
            );
        }

        Inventory inventory =
                getInventoryById(inventoryId);

        int currentStock =
                inventory.getAvailableQuantity() == null
                        ? 0
                        : inventory.getAvailableQuantity();

        if (quantity > currentStock) {
            throw new RuntimeException(
                    "Insufficient stock"
            );
        }

        inventory.setAvailableQuantity(
                currentStock - quantity
        );

        updateStockStatus(inventory);

        return inventoryRepository.save(inventory);
    }

    // LOW STOCK CHECK
    public boolean isLowStock(Long inventoryId) {

        Inventory inventory =
                getInventoryById(inventoryId);

        int available =
                inventory.getAvailableQuantity() == null
                        ? 0
                        : inventory.getAvailableQuantity();

        int reorderLevel =
                inventory.getReorderLevel() == null
                        ? 0
                        : inventory.getReorderLevel();

        return available <= reorderLevel;
    }

    // STOCK STATUS
    private void updateStockStatus(
            Inventory inventory) {

        int available =
                inventory.getAvailableQuantity() == null
                        ? 0
                        : inventory.getAvailableQuantity();

        int reorderLevel =
                inventory.getReorderLevel() == null
                        ? 0
                        : inventory.getReorderLevel();

        if (available == 0) {
            inventory.setStatus("OUT_OF_STOCK");
        } else if (available <= reorderLevel) {
            inventory.setStatus("LOW_STOCK");
        } else {
            inventory.setStatus("AVAILABLE");
        }
    }
}

