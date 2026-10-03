package com.InstituteManagement.Repository;

import com.InstituteManagement.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);
    List<Payment> findByBatchId(Long batchId);
    List<Payment> findByCourseId(Long courseId);
}
