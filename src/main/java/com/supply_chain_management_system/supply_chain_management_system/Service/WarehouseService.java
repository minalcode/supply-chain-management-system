package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Warehouse;
import com.supply_chain_management_system.supply_chain_management_system.Repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(
            WarehouseRepository warehouseRepository) {

        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {

        return warehouseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Warehouse not found"
                        ));
    }

    public Warehouse updateWarehouse(
            Long id,
            Warehouse warehouse) {

        Warehouse existing =
                getWarehouseById(id);

        existing.setWarehouseName(
                warehouse.getWarehouseName()
        );

        existing.setWarehouseCode(
                warehouse.getWarehouseCode()
        );

        existing.setAddress(
                warehouse.getAddress()
        );

        existing.setManagerName(
                warehouse.getManagerName()
        );

        return warehouseRepository.save(existing);
    }

    public void deleteWarehouse(Long id) {

        if (!warehouseRepository.existsById(id)) {
            throw new RuntimeException(
                    "Warehouse not found"
            );
        }

        warehouseRepository.deleteById(id);
    }

    // Pagination
    public Page<Warehouse> getWarehouses(
            Pageable pageable) {

        return warehouseRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<Warehouse> searchWarehouses(
            String keyword,
            Pageable pageable) {

        return warehouseRepository
                .findByWarehouseNameContainingIgnoreCaseOrWarehouseCodeContainingIgnoreCase(
                        keyword,
                        keyword,
                        pageable
                );
    }
}