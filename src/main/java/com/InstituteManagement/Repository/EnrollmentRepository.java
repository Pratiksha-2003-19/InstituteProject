package com.InstituteManagement.Repository;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndBatchId(Long studentId, Long batchId);

    long countByBatchIdAndStatus(Long batchId, Enrollment.EnrollmentStatus status);

    List<Enrollment> findByBatchId(Long batchId);

    List<Enrollment> findByStudentId(Long studentId);

    long countByBatch(Batch batch);
}
