package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Company;
import com.supply_chain_management_system.supply_chain_management_system.Model.PurchaseOrder;
import com.supply_chain_management_system.supply_chain_management_system.Model.Supplier;
import com.supply_chain_management_system.supply_chain_management_system.Repository.CompanyRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.PurchaseOrderRepository;
import com.supply_chain_management_system.supply_chain_management_system.Repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            CompanyRepository companyRepository) {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.companyRepository = companyRepository;
    }

    public PurchaseOrder createPurchaseOrder(
            PurchaseOrder order,
            Long supplierId,
            Long companyId) {

        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                ));

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Company not found"
                                ));

        order.setSupplier(supplier);
        order.setCompany(company);

        return purchaseOrderRepository.save(order);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {

        return purchaseOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase order not found"
                        ));
    }

    public void deletePurchaseOrder(Long id) {

        if (!purchaseOrderRepository.existsById(id)) {
            throw new RuntimeException(
                    "Purchase order not found"
            );
        }

        purchaseOrderRepository.deleteById(id);
    }

    // Pagination
    public Page<PurchaseOrder> getPurchaseOrders(
            Pageable pageable) {

        return purchaseOrderRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<PurchaseOrder> searchPurchaseOrders(
            String keyword,
            Pageable pageable) {

        return purchaseOrderRepository
                .findByOrderNumberContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }
}