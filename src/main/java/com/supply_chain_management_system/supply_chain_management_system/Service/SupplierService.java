package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Supplier;
import com.supply_chain_management_system.supply_chain_management_system.Repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Long id) {

        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found"));
    }

    public Supplier updateSupplier(
            Long id,
            Supplier supplier) {

        Supplier existingSupplier =
                getSupplierById(id);

        existingSupplier.setSupplierName(
                supplier.getSupplierName()
        );

        existingSupplier.setEmail(
                supplier.getEmail()
        );

        existingSupplier.setPhone(
                supplier.getPhone()
        );

        existingSupplier.setAddress(
                supplier.getAddress()
        );

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id) {

        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }

        supplierRepository.deleteById(id);
    }

    // Pagination
    public Page<Supplier> getSuppliers(Pageable pageable) {

        return supplierRepository.findAll(pageable);
    }

    // Search
    public Page<Supplier> searchSuppliers(
            String keyword,
            Pageable pageable) {

        return supplierRepository
                .findBySupplierNameContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }
}