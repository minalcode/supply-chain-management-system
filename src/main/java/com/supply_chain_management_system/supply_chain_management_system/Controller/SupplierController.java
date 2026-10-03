package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.SupplierDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Supplier;
import com.supply_chain_management_system.supply_chain_management_system.Service.SupplierService;
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
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    public SupplierController(SupplierService supplierService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@Valid @RequestBody SupplierDTO dto) {
        Supplier supplier = modelMapper.map(dto, Supplier.class);
        Supplier saved = supplierService.createSupplier(supplier);
        return ResponseEntity.ok(modelMapper.map(saved, SupplierDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers()
                .stream()
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class))
                .toList();

        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(
                modelMapper.map(
                        supplierService.getSupplierById(id),
                        SupplierDTO.class
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody SupplierDTO dto) {

        Supplier supplier = modelMapper.map(dto, Supplier.class);
        Supplier updated = supplierService.updateSupplier(id, supplier);

        return ResponseEntity.ok(
                modelMapper.map(updated, SupplierDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully");
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SupplierDTO>> getSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SupplierDTO> suppliers = supplierService
                .getSuppliers(pageable)
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));

        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SupplierDTO>> searchSuppliers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SupplierDTO> suppliers = supplierService
                .searchSuppliers(keyword, pageable)
                .map(supplier -> modelMapper.map(supplier, SupplierDTO.class));

        return ResponseEntity.ok(suppliers);
    }
}