package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.FeeStructure;
import com.InstituteManagement.Service.FeeStructureService;
import com.InstituteManagement.dto.CreateFeeStructureRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/fee-structures")
    public ResponseEntity<FeeStructure> createFeeStructure(@Valid @RequestBody CreateFeeStructureRequest request) {
        return ResponseEntity.ok(feeStructureService.createFeeStructure(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/fee-structures")
    public ResponseEntity<List<FeeStructure>> getAllFeeStructures() {
        return ResponseEntity.ok(feeStructureService.getAllFeeStructures());
    }
}
