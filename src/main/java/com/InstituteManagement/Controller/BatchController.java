package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Service.BatchService;
import com.InstituteManagement.dto.CreateBatchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/createBatch")
    public ResponseEntity<Batch> createBatch(@Valid @RequestBody CreateBatchRequest request) {
        return ResponseEntity.ok(batchService.createBatch(request));
    }

    @GetMapping("/allBatches")
    public ResponseEntity<List<Batch>> getAllBatches() {
        return ResponseEntity.ok(batchService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batch> getBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(batchService.getBatchById(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Batch>> getBatchesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(batchService.getBatchesByCourseId(courseId));
    }
}
