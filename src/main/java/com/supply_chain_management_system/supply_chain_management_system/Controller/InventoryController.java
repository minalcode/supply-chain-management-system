
        package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.InventoryDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Inventory;
import com.supply_chain_management_system.supply_chain_management_system.Service.InventoryService;
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
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    public InventoryController(
            InventoryService inventoryService,
            ModelMapper modelMapper) {

        this.inventoryService = inventoryService;
        this.modelMapper = modelMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<InventoryDTO> create(
            @Valid @RequestBody InventoryDTO dto) {

        Inventory inventory =
                modelMapper.map(dto, Inventory.class);

        Inventory saved =
                inventoryService.createInventory(
                        inventory,
                        dto.getProductId(),
                        dto.getWarehouseId()
                );

        return ResponseEntity.ok(
                convertToDTO(saved)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAll() {

        List<InventoryDTO> inventory =
                inventoryService.getAllInventory()
                        .stream()
                        .map(this::convertToDTO)
                        .toList();

        return ResponseEntity.ok(inventory);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getById(
            @PathVariable Long id) {

        Inventory inventory =
                inventoryService.getInventoryById(id);

        return ResponseEntity.ok(
                convertToDTO(inventory)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        inventoryService.deleteInventory(id);

        return ResponseEntity.ok(
                "Inventory deleted successfully"
        );
    }

    // PAGINATION + SORTING
    @GetMapping("/page")
    public ResponseEntity<Page<InventoryDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<InventoryDTO> inventory =
                inventoryService
                        .getInventory(pageable)
                        .map(this::convertToDTO);

        return ResponseEntity.ok(inventory);
    }

    // SEARCH + PAGINATION + SORTING
    @GetMapping("/search")
    public ResponseEntity<Page<InventoryDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<InventoryDTO> inventory =
                inventoryService
                        .searchInventory(
                                keyword,
                                pageable
                        )
                        .map(this::convertToDTO);

        return ResponseEntity.ok(inventory);
    }

    // ADD STOCK
    @PutMapping("/{id}/add-stock")
    public ResponseEntity<InventoryDTO> addStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        Inventory inventory =
                inventoryService.addStock(
                        id,
                        quantity
                );

        return ResponseEntity.ok(
                convertToDTO(inventory)
        );
    }

    // REMOVE STOCK
    @PutMapping("/{id}/remove-stock")
    public ResponseEntity<InventoryDTO> removeStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        Inventory inventory =
                inventoryService.removeStock(
                        id,
                        quantity
                );

        return ResponseEntity.ok(
                convertToDTO(inventory)
        );
    }

    // LOW STOCK CHECK
    @GetMapping("/{id}/low-stock")
    public ResponseEntity<Boolean> checkLowStock(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inventoryService.isLowStock(id)
        );
    }

    // DTO CONVERSION
    private InventoryDTO convertToDTO(
            Inventory inventory) {

        InventoryDTO dto =
                modelMapper.map(
                        inventory,
                        InventoryDTO.class
                );

        dto.setProductId(
                inventory.getProduct().getId()
        );

        dto.setWarehouseId(
                inventory.getWarehouse().getId()
        );

        return dto;
    }
}

