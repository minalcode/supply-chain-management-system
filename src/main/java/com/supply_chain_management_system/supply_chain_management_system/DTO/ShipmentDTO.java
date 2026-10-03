package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    @NotNull(message = "Purchase order ID is required")
    private Long purchaseOrderId;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDate shipmentDate;

    private LocalDate expectedDeliveryDate;

    private LocalDate actualDeliveryDate;
}