package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {

    private Long id;

    @NotBlank(message = "Order number is required")
    private String orderNumber;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @Positive(message = "Total amount must be greater than 0")
    private Double totalAmount;

    @NotBlank(message = "Payment terms are required")
    private String paymentTerms;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDate orderDate;

    private LocalDate expectedDeliveryDate;
}