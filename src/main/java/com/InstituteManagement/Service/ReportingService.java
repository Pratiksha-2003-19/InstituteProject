package com.InstituteManagement.Service;

import com.InstituteManagement.Repository.*;
import com.InstituteManagement.dto.ReportSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentRepository paymentRepository;
    private final CertificateRepository certificateRepository;
    private final NotificationRepository notificationRepository;

    public ReportSummaryResponse getAdminSummary() {
        long totalStudents = studentRepository.count();
        long totalTrainers = userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "TRAINER".equalsIgnoreCase(role.getName())))
                .count();
        long totalCourses = courseRepository.count();
        long totalBatches = batchRepository.count();
        long totalEnrollments = enrollmentRepository.count();

        BigDecimal totalRevenue = paymentRepository.findAll().stream()
                .map(payment -> payment.getAmount() == null ? BigDecimal.ZERO : payment.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pendingFees = BigDecimal.ZERO;
        long issuedCertificates = certificateRepository.countByStatus("ISSUED");
        long unreadNotifications = notificationRepository.countByReadFalse();
        long activeCourses = courseRepository.findAll().stream().filter(course -> "ACTIVE".equalsIgnoreCase(course.getStatus())).count();
        long activeBatches = batchRepository.findAll().stream().filter(batch -> "ACTIVE".equalsIgnoreCase(batch.getStatus())).count();

        return new ReportSummaryResponse(
                totalStudents,
                totalTrainers,
                totalCourses,
                totalBatches,
                totalEnrollments,
                totalRevenue,
                pendingFees,
                issuedCertificates,
                unreadNotifications,
                activeCourses,
                activeBatches
        );
    }
}
