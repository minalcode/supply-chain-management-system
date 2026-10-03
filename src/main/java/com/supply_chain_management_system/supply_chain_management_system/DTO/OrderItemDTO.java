package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private Long id;

    @NotNull(message = "Purchase order ID is required")
    private Long purchaseOrderId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @Positive(message = "Unit price must be greater than 0")
    private Double unitPrice;

    @Positive(message = "Total price must be greater than 0")
    private Double totalPrice;
}