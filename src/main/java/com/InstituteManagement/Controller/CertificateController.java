package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Certificate;
import com.InstituteManagement.Service.CertificateService;
import com.InstituteManagement.dto.IssueCertificateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/certificates")
    public ResponseEntity<Certificate> issueCertificate(@Valid @RequestBody IssueCertificateRequest request) {
        return ResponseEntity.ok(certificateService.issueCertificate(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/certificates")
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/students/{studentId}/certificates")
    public ResponseEntity<List<Certificate>> getStudentCertificates(@PathVariable Long studentId) {
        return ResponseEntity.ok(certificateService.getCertificatesForStudent(studentId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/certificates/{certificateNumber}/download")
    public ResponseEntity<String> downloadCertificate(@PathVariable String certificateNumber) {
        return ResponseEntity.ok("Certificate download endpoint ready for: " + certificateNumber);
    }
}
