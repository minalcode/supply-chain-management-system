package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.ProcurementRequestDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.ProcurementRequest;
import com.supply_chain_management_system.supply_chain_management_system.Service.ProcurementRequestService;
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
@RequestMapping("/api/procurement-requests")
public class ProcurementRequestController {

    private final ProcurementRequestService procurementRequestService;
    private final ModelMapper modelMapper;

    public ProcurementRequestController(
            ProcurementRequestService procurementRequestService,
            ModelMapper modelMapper) {

        this.procurementRequestService = procurementRequestService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ProcurementRequestDTO> create(
            @Valid @RequestBody ProcurementRequestDTO dto) {

        ProcurementRequest request =
                modelMapper.map(dto, ProcurementRequest.class);

        ProcurementRequest saved =
                procurementRequestService.createProcurementRequest(request);

        return ResponseEntity.ok(
                modelMapper.map(saved, ProcurementRequestDTO.class)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProcurementRequestDTO>> getAll() {

        List<ProcurementRequestDTO> requests =
                procurementRequestService.getAllProcurementRequests()
                        .stream()
                        .map(request ->
                                modelMapper.map(
                                        request,
                                        ProcurementRequestDTO.class
                                )
                        )
                        .toList();

        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcurementRequestDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                modelMapper.map(
                        procurementRequestService
                                .getProcurementRequestById(id),
                        ProcurementRequestDTO.class
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcurementRequestDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProcurementRequestDTO dto) {

        ProcurementRequest request =
                modelMapper.map(dto, ProcurementRequest.class);

        ProcurementRequest updated =
                procurementRequestService
                        .updateProcurementRequest(id, request);

        return ResponseEntity.ok(
                modelMapper.map(
                        updated,
                        ProcurementRequestDTO.class
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        procurementRequestService.deleteProcurementRequest(id);

        return ResponseEntity.ok(
                "Procurement request deleted successfully"
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProcurementRequestDTO>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProcurementRequestDTO> requests =
                procurementRequestService
                        .getProcurementRequests(pageable)
                        .map(request ->
                                modelMapper.map(
                                        request,
                                        ProcurementRequestDTO.class
                                )
                        );

        return ResponseEntity.ok(requests);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProcurementRequestDTO>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProcurementRequestDTO> requests =
                procurementRequestService
                        .searchProcurementRequests(keyword, pageable)
                        .map(request ->
                                modelMapper.map(
                                        request,
                                        ProcurementRequestDTO.class
                                )
                        );

        return ResponseEntity.ok(requests);
    }
}