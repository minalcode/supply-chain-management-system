package com.supply_chain_management_system.supply_chain_management_system.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "warehouses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String warehouseName;

    private String warehouseCode;

    private String address;

    private String city;

    private String state;

    private String country;

    private String managerName;

    private String contactNumber;

    private String status;
}