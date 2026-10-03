package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Enrollment;
import com.InstituteManagement.Model.Student;
import com.InstituteManagement.Repository.BatchRepository;
import com.InstituteManagement.Repository.EnrollmentRepository;
import com.InstituteManagement.Repository.StudentRepository;
import com.InstituteManagement.dto.CreateEnrollmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;

    public Enrollment createEnrollment(CreateEnrollmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        Batch batch = batchRepository.findById(request.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));

        if (enrollmentRepository.existsByStudentIdAndBatchId(student.getId(), batch.getId())) {
            throw new RuntimeException("Student is already enrolled in this batch");
        }

        long approvedStudents = enrollmentRepository.countByBatchIdAndStatus(batch.getId(), Enrollment.EnrollmentStatus.APPROVED);
        Enrollment.EnrollmentStatus status = approvedStudents < batch.getMaxStudents()
                ? Enrollment.EnrollmentStatus.APPROVED
                : Enrollment.EnrollmentStatus.WAITLIST;

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setBatch(batch);
        enrollment.setStatus(status);

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByBatch(Long batchId) {
        return enrollmentRepository.findByBatchId(batchId);
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
