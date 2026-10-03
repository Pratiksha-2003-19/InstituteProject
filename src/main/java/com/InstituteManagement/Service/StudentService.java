package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Student;
import com.InstituteManagement.Repository.BatchRepository;
import com.InstituteManagement.Repository.StudentRepository;
import com.InstituteManagement.dto.CreateStudentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;

    public Student createStudent(CreateStudentRequest request) {
        Student s = new Student();
        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setPhone(request.getPhone());
        if (request.getBatchId() != null) {
            Batch batch = batchRepository.findById(request.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));
            s.setBatch(batch);
        }
        s.setStatus("ACTIVE");
        return studentRepository.save(s);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public List<Student> getStudentsByBatchId(Long batchId) {
        return studentRepository.findByBatchId(batchId);
    }

    public Student assignStudentToBatch(Long studentId, Long batchId) {
        Student s = getStudentById(studentId);
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + batchId));
        s.setBatch(batch);
        return studentRepository.save(s);
    }
}
