package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Certificate;
import com.InstituteManagement.Model.Course;
import com.InstituteManagement.Model.Student;
import com.InstituteManagement.Repository.CertificateRepository;
import com.InstituteManagement.Repository.CourseRepository;
import com.InstituteManagement.Repository.StudentRepository;
import com.InstituteManagement.dto.IssueCertificateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Certificate issueCertificate(IssueCertificateRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        Certificate certificate = new Certificate();
        certificate.setStudent(student);
        certificate.setCourse(course);
        certificate.setCertificateNumber("CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        certificate.setGrade(request.getGrade().toUpperCase());
        certificate.setStatus("ISSUED");
        certificate.setIssuedAt(LocalDateTime.now());
        certificate.setPdfUrl("/api/certificates/" + certificate.getCertificateNumber() + "/download");
        certificate.setQrCodeData("CERT=" + certificate.getCertificateNumber() + "|STUDENT=" + student.getId() + "|COURSE=" + course.getId());

        return certificateRepository.save(certificate);
    }

    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    public List<Certificate> getCertificatesForStudent(Long studentId) {
        return certificateRepository.findByStudentId(studentId);
    }

}
