package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Student;
import com.InstituteManagement.Service.StudentService;
import com.InstituteManagement.dto.CreateStudentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Student>> getStudentsByBatch(@PathVariable Long batchId) {
        return ResponseEntity.ok(studentService.getStudentsByBatchId(batchId));
    }

    @PostMapping("/{id}/assign/{batchId}")
    public ResponseEntity<Student> assignToBatch(@PathVariable Long id, @PathVariable Long batchId) {
        return ResponseEntity.ok(studentService.assignStudentToBatch(id, batchId));
    }
}
