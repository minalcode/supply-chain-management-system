package com.supply_chain_management_system.supply_chain_management_system.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "procurement_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestNumber;

    private String requestedBy;

    private String department;

    private String description;

    private Integer quantity;

    private String priority;

    private String status;

    private LocalDate requestDate;
}