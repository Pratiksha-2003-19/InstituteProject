package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Attendance;
import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Student;
import com.InstituteManagement.Repository.AttendanceRepository;
import com.InstituteManagement.Repository.BatchRepository;
import com.InstituteManagement.Repository.StudentRepository;
import com.InstituteManagement.dto.CreateAttendanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;

    public Attendance createAttendance(CreateAttendanceRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + request.getStudentId()));

        Batch batch = null;
        if (request.getBatchId() != null) {
            batch = batchRepository.findById(request.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setBatch(batch);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus().toUpperCase());
        attendance.setRemarks(request.getRemarks());

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public List<Attendance> getAttendanceForStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
}
