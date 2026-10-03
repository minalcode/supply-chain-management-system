package com.supply_chain_management_system.supply_chain_management_system.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO {

    private Long id;

    @NotBlank(message = "Warehouse name is required")
    private String warehouseName;

    @NotBlank(message = "Warehouse code is required")
    private String warehouseCode;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Manager name is required")
    private String managerName;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotBlank(message = "Status is required")
    private String status;
}