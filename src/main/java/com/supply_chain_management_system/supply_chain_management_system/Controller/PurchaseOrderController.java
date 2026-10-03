package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.PurchaseOrderDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.PurchaseOrder;
import com.supply_chain_management_system.supply_chain_management_system.Service.PurchaseOrderService;
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
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final ModelMapper modelMapper;

    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService,
            ModelMapper modelMapper) {

        this.purchaseOrderService = purchaseOrderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> create(
            @Valid @RequestBody PurchaseOrderDTO dto) {

        PurchaseOrder order =
                modelMapper.map(dto, PurchaseOrder.class);

        PurchaseOrder saved =
                purchaseOrderService.createPurchaseOrder(
                        order,
                        dto.getSupplierId(),
                        dto.getCompanyId()
                );

        PurchaseOrderDTO response =
                modelMapper.map(saved, PurchaseOrderDTO.class);

        response.setSupplierId(saved.getSupplier().getId());
        response.setCompanyId(saved.getCompany().getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAll() {

        List<PurchaseOrderDTO> orders =
                purchaseOrderService.getAllPurchaseOrders()
                        .stream()
                        .map(order -> {

                            PurchaseOrderDTO dto =
                                    modelMapper.map(
                                            order,
                                            PurchaseOrderDTO.class
                                    );

                            dto.setSupplierId(
                                    order.getSupplier().getId()
                            );

                            dto.setCompanyId(
                                    order.getCompany().getId()
                            );

                            return dto;
                        })
                        .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getById(
            @PathVariable Long id) {

        PurchaseOrder order =
                purchaseOrderService.getPurchaseOrderById(id);

        PurchaseOrderDTO dto =
                modelMapper.map(order, PurchaseOrderDTO.class);

        dto.setSupplierId(order.getSupplier().getId());
        dto.setCompanyId(order.getCompany().getId());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        purchaseOrderService.deletePurchaseOrder(id);

        return ResponseEntity.ok(
                "Purchase order deleted successfully"
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PurchaseOrderDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PurchaseOrderDTO> orders =
                purchaseOrderService.getPurchaseOrders(pageable)
                        .map(order -> {

                            PurchaseOrderDTO dto =
                                    modelMapper.map(
                                            order,
                                            PurchaseOrderDTO.class
                                    );

                            dto.setSupplierId(
                                    order.getSupplier().getId()
                            );

                            dto.setCompanyId(
                                    order.getCompany().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PurchaseOrderDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PurchaseOrderDTO> orders =
                purchaseOrderService
                        .searchPurchaseOrders(keyword, pageable)
                        .map(order -> {

                            PurchaseOrderDTO dto =
                                    modelMapper.map(
                                            order,
                                            PurchaseOrderDTO.class
                                    );

                            dto.setSupplierId(
                                    order.getSupplier().getId()
                            );

                            dto.setCompanyId(
                                    order.getCompany().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(orders);
    }
}