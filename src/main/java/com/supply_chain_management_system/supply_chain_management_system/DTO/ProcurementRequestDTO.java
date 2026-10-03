package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementRequestDTO {

    private Long id;

    @NotBlank(message = "Request number is required")
    private String requestNumber;

    @NotBlank(message = "Requested by is required")
    private String requestedBy;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotBlank(message = "Priority is required")
    private String priority;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDate requestDate;
}