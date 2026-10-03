package com.InstituteManagement.Repository;

import com.InstituteManagement.Model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByBatchId(Long batchId);
    List<Exam> findByCourseId(Long courseId);
}
