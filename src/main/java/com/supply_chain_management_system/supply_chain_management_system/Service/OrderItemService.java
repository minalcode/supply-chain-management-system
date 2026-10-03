package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.OrderItem;
import com.supply_chain_management_system.supply_chain_management_system.Model.Product;
import com.supply_chain_management_system.supply_chain_management_system.Model.PurchaseOrder;
import com.supply_chain_management_system.supply_chain_management_system.Repository.OrderItemRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.ProductRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.PurchaseOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(
            OrderItemRepository orderItemRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            ProductRepository productRepository) {

        this.orderItemRepository = orderItemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
    }

    public OrderItem createOrderItem(
            OrderItem item,
            Long purchaseOrderId,
            Long productId) {

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                        purchaseOrderId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Purchase order not found"
                        ));

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"
                                ));

        item.setPurchaseOrder(purchaseOrder);
        item.setProduct(product);

        return orderItemRepository.save(item);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {

        return orderItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order item not found"
                        ));
    }

    public void deleteOrderItem(Long id) {

        if (!orderItemRepository.existsById(id)) {
            throw new RuntimeException(
                    "Order item not found"
            );
        }

        orderItemRepository.deleteById(id);
    }

    // Pagination
    public Page<OrderItem> getOrderItems(
            Pageable pageable) {

        return orderItemRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<OrderItem> searchOrderItems(
            String keyword,
            Pageable pageable) {

        return orderItemRepository.searchOrderItems(
                keyword,
                pageable
        );
    }
}