package com.InstituteManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReportSummaryResponse {
    private long totalStudents;
    private long totalTrainers;
    private long totalCourses;
    private long totalBatches;
    private long totalEnrollments;
    private BigDecimal totalRevenue;
    private BigDecimal pendingFees;
    private long issuedCertificates;
    private long unreadNotifications;
    private long activeCourses;
    private long activeBatches;
}
