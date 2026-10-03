package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Enrollment;
import com.InstituteManagement.Service.EnrollmentService;
import com.InstituteManagement.dto.CreateEnrollmentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/createEnrollment")
    public ResponseEntity<Enrollment> createEnrollment(@Valid @RequestBody CreateEnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(request));
    }

    @GetMapping("/allEnrollments")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Enrollment>> getByBatch(@PathVariable Long batchId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByBatch(batchId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId));
    }
}
