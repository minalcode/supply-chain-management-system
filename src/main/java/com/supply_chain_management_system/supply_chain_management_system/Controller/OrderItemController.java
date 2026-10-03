package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.OrderItemDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.OrderItem;
import com.supply_chain_management_system.supply_chain_management_system.Service.OrderItemService;
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
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final ModelMapper modelMapper;

    public OrderItemController(
            OrderItemService orderItemService,
            ModelMapper modelMapper) {

        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> create(
            @Valid @RequestBody OrderItemDTO dto) {

        OrderItem item =
                modelMapper.map(dto, OrderItem.class);

        OrderItem saved =
                orderItemService.createOrderItem(
                        item,
                        dto.getPurchaseOrderId(),
                        dto.getProductId()
                );

        OrderItemDTO response =
                modelMapper.map(saved, OrderItemDTO.class);

        response.setPurchaseOrderId(
                saved.getPurchaseOrder().getId()
        );

        response.setProductId(
                saved.getProduct().getId()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAll() {

        List<OrderItemDTO> items =
                orderItemService.getAllOrderItems()
                        .stream()
                        .map(item -> {

                            OrderItemDTO dto =
                                    modelMapper.map(
                                            item,
                                            OrderItemDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    item.getPurchaseOrder().getId()
                            );

                            dto.setProductId(
                                    item.getProduct().getId()
                            );

                            return dto;
                        })
                        .toList();

        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getById(
            @PathVariable Long id) {

        OrderItem item =
                orderItemService.getOrderItemById(id);

        OrderItemDTO dto =
                modelMapper.map(item, OrderItemDTO.class);

        dto.setPurchaseOrderId(
                item.getPurchaseOrder().getId()
        );

        dto.setProductId(
                item.getProduct().getId()
        );

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        orderItemService.deleteOrderItem(id);

        return ResponseEntity.ok(
                "Order item deleted successfully"
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<OrderItemDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrderItemDTO> items =
                orderItemService.getOrderItems(pageable)
                        .map(item -> {

                            OrderItemDTO dto =
                                    modelMapper.map(
                                            item,
                                            OrderItemDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    item.getPurchaseOrder().getId()
                            );

                            dto.setProductId(
                                    item.getProduct().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderItemDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<OrderItemDTO> items =
                orderItemService
                        .searchOrderItems(keyword, pageable)
                        .map(item -> {

                            OrderItemDTO dto =
                                    modelMapper.map(
                                            item,
                                            OrderItemDTO.class
                                    );

                            dto.setPurchaseOrderId(
                                    item.getPurchaseOrder().getId()
                            );

                            dto.setProductId(
                                    item.getProduct().getId()
                            );

                            return dto;
                        });

        return ResponseEntity.ok(items);
    }
}