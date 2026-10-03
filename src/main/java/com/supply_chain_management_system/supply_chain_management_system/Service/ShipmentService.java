package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.PurchaseOrder;
import com.supply_chain_management_system.supply_chain_management_system.Model.Shipment;
import com.supply_chain_management_system.supply_chain_management_system.Repository.PurchaseOrderRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.ShipmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            PurchaseOrderRepository purchaseOrderRepository) {

        this.shipmentRepository = shipmentRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Shipment createShipment(
            Shipment shipment,
            Long purchaseOrderId) {

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                        purchaseOrderId
                ).orElseThrow(() ->
                        new RuntimeException(
                                "Purchase order not found"
                        ));

        shipment.setPurchaseOrder(purchaseOrder);

        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {

        return shipmentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shipment not found"
                        ));
    }

    public void deleteShipment(Long id) {

        if (!shipmentRepository.existsById(id)) {
            throw new RuntimeException(
                    "Shipment not found"
            );
        }

        shipmentRepository.deleteById(id);
    }

    // Pagination
    public Page<Shipment> getShipments(
            Pageable pageable) {

        return shipmentRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<Shipment> searchShipments(
            String keyword,
            Pageable pageable) {

        return shipmentRepository
                .findByTrackingNumberContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }
}