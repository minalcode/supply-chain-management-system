package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.WarehouseDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Warehouse;
import com.supply_chain_management_system.supply_chain_management_system.Service.WarehouseService;
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
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    public WarehouseController(
            WarehouseService warehouseService,
            ModelMapper modelMapper) {

        this.warehouseService = warehouseService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<WarehouseDTO> create(
            @Valid @RequestBody WarehouseDTO dto) {

        Warehouse warehouse =
                modelMapper.map(dto, Warehouse.class);

        Warehouse saved =
                warehouseService.createWarehouse(warehouse);

        return ResponseEntity.ok(
                modelMapper.map(saved, WarehouseDTO.class)
        );
    }

    @GetMapping
    public ResponseEntity<List<WarehouseDTO>> getAll() {

        List<WarehouseDTO> warehouses =
                warehouseService.getAllWarehouses()
                        .stream()
                        .map(warehouse ->
                                modelMapper.map(
                                        warehouse,
                                        WarehouseDTO.class
                                )
                        )
                        .toList();

        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                modelMapper.map(
                        warehouseService.getWarehouseById(id),
                        WarehouseDTO.class
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseDTO dto) {

        Warehouse warehouse =
                modelMapper.map(dto, Warehouse.class);

        Warehouse updated =
                warehouseService.updateWarehouse(
                        id,
                        warehouse
                );

        return ResponseEntity.ok(
                modelMapper.map(
                        updated,
                        WarehouseDTO.class
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        warehouseService.deleteWarehouse(id);

        return ResponseEntity.ok(
                "Warehouse deleted successfully"
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<WarehouseDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<WarehouseDTO> warehouses =
                warehouseService.getWarehouses(pageable)
                        .map(warehouse ->
                                modelMapper.map(
                                        warehouse,
                                        WarehouseDTO.class
                                )
                        );

        return ResponseEntity.ok(warehouses);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<WarehouseDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<WarehouseDTO> warehouses =
                warehouseService
                        .searchWarehouses(keyword, pageable)
                        .map(warehouse ->
                                modelMapper.map(
                                        warehouse,
                                        WarehouseDTO.class
                                )
                        );

        return ResponseEntity.ok(warehouses);
    }
}