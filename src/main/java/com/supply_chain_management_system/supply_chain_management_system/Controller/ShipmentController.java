package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.ShipmentDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Shipment;
import com.supply_chain_management_system.supply_chain_management_system.Service.ShipmentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ModelMapper modelMapper;

    public ShipmentController(
            ShipmentService shipmentService,
            ModelMapper modelMapper) {

        this.shipmentService = shipmentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ShipmentDTO> create(
            @Valid @RequestBody ShipmentDTO dto) {

        Shipment shipment =
                modelMapper.map(dto, Shipment.class);

        Shipment saved =
                shipmentService.createShipment(
                        shipment,
                        dto.getPurchaseOrderId()
                );

        ShipmentDTO response =
                modelMapper.map(saved, ShipmentDTO.class);

        response.setPurchaseOrderId(
                saved.getPurchaseOrder().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ShipmentDTO>> getAll() {

        List<ShipmentDTO> shipments =
                shipmentService.getAllShipments()
                        .stream()
                        .map(shipment -> {

                            ShipmentDTO dto =
                                    modelMapper.map(
                                            shipment,
                                            ShipmentDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    shipment.getPurchaseOrder().getId()
                            );

                            return dto;
                        })
                        .toList();

        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDTO> getById(
            @PathVariable Long id) {

        Shipment shipment =
                shipmentService.getShipmentById(id);

        ShipmentDTO dto =
                modelMapper.map(
                        shipment,
                        ShipmentDTO.class
                );

        dto.setPurchaseOrderId(
                shipment.getPurchaseOrder().getId()
        );

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        shipmentService.deleteShipment(id);

        return ResponseEntity.ok(
                "Shipment deleted successfully"
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ShipmentDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ShipmentDTO> shipments =
                shipmentService.getShipments(pageable)
                        .map(shipment -> {

                            ShipmentDTO dto =
                                    modelMapper.map(
                                            shipment,
                                            ShipmentDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    shipment.getPurchaseOrder().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ShipmentDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ShipmentDTO> shipments =
                shipmentService
                        .searchShipments(keyword, pageable)
                        .map(shipment -> {

                            ShipmentDTO dto =
                                    modelMapper.map(
                                            shipment,
                                            ShipmentDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    shipment.getPurchaseOrder().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(shipments);
    }
}