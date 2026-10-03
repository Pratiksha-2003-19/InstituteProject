package com.InstituteManagement.Service;

import com.InstituteManagement.Model.*;
import com.InstituteManagement.Repository.*;
import com.InstituteManagement.dto.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;
    private final FeeStructureRepository feeStructureRepository;

    public Payment createPayment(CreatePaymentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        Batch batch = null;
        if (request.getBatchId() != null) {
            batch = batchRepository.findById(request.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));
        }

        FeeStructure feeStructure = null;
        if (request.getFeeStructureId() != null) {
            feeStructure = feeStructureRepository.findById(request.getFeeStructureId())
                    .orElseThrow(() -> new RuntimeException("Fee structure not found with id: " + request.getFeeStructureId()));
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }

        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setBatch(batch);
        payment.setCourse(course);
        payment.setFeeStructure(feeStructure);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("PAID");
        payment.setTransactionRef(request.getTransactionRef() != null ? request.getTransactionRef() : UUID.randomUUID().toString());

        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsForStudent(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
