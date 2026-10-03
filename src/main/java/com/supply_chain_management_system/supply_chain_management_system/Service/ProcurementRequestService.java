package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.ProcurementRequest;
import com.supply_chain_management_system.supply_chain_management_system.Repository.ProcurementRequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcurementRequestService {

    private final ProcurementRequestRepository procurementRequestRepository;

    public ProcurementRequestService(
            ProcurementRequestRepository procurementRequestRepository) {

        this.procurementRequestRepository =
                procurementRequestRepository;
    }

    // Create
    public ProcurementRequest createProcurementRequest(
            ProcurementRequest request) {

        return procurementRequestRepository.save(request);
    }

    // Get All
    public List<ProcurementRequest> getAllProcurementRequests() {

        return procurementRequestRepository.findAll();
    }

    // Get By ID
    public ProcurementRequest getProcurementRequestById(Long id) {

        return procurementRequestRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Procurement request not found"
                        ));
    }

    // Update
    public ProcurementRequest updateProcurementRequest(
            Long id,
            ProcurementRequest request) {

        ProcurementRequest existing =
                getProcurementRequestById(id);

        existing.setRequestNumber(
                request.getRequestNumber()
        );

        existing.setRequestedBy(
                request.getRequestedBy()
        );

        existing.setDepartment(
                request.getDepartment()
        );

        existing.setDescription(
                request.getDescription()
        );

        existing.setQuantity(
                request.getQuantity()
        );

        existing.setPriority(
                request.getPriority()
        );

        existing.setStatus(
                request.getStatus()
        );

        existing.setRequestDate(
                request.getRequestDate()
        );

        return procurementRequestRepository.save(existing);
    }

    // Delete
    public void deleteProcurementRequest(Long id) {

        if (!procurementRequestRepository.existsById(id)) {
            throw new RuntimeException(
                    "Procurement request not found"
            );
        }

        procurementRequestRepository.deleteById(id);
    }

    // Pagination
    public Page<ProcurementRequest> getProcurementRequests(
            Pageable pageable) {

        return procurementRequestRepository.findAll(pageable);
    }

    // Search + Pagination
    public Page<ProcurementRequest> searchProcurementRequests(
            String keyword,
            Pageable pageable) {

        return procurementRequestRepository
                .findByRequestNumberContainingIgnoreCase(
                        keyword,
                        pageable
                );
    }
}