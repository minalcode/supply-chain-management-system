package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @PositiveOrZero(message = "Available quantity cannot be negative")
    private Integer availableQuantity;

    @PositiveOrZero(message = "Reserved quantity cannot be negative")
    private Integer reservedQuantity;

    @PositiveOrZero(message = "Reorder level cannot be negative")
    private Integer reorderLevel;

    @NotBlank(message = "Status is required")
    private String status;
}