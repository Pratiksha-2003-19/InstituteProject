package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Attendance;
import com.InstituteManagement.Service.AttendanceService;
import com.InstituteManagement.dto.CreateAttendanceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    @PostMapping("/admin/attendance")
    public ResponseEntity<Attendance> createAttendance(@Valid @RequestBody CreateAttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.createAttendance(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'STUDENT')")
    @GetMapping("/attendance")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'STUDENT')")
    @GetMapping("/students/{studentId}/attendance")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getAttendanceForStudent(studentId));
    }
}
