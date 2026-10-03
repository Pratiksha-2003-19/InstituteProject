package com.InstituteManagement.Controller;

import com.InstituteManagement.Service.ReportingService;
import com.InstituteManagement.dto.ReportSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportingController {

    private final ReportingService reportingService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/reports/summary")
    public ResponseEntity<ReportSummaryResponse> getReportSummary() {
        return ResponseEntity.ok(reportingService.getAdminSummary());
    }
}
