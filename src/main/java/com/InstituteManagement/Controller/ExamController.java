package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.Exam;
import com.InstituteManagement.Model.ExamAttempt;
import com.InstituteManagement.Model.Question;
import com.InstituteManagement.Service.ExamService;
import com.InstituteManagement.dto.CreateExamRequest;
import com.InstituteManagement.dto.CreateQuestionRequest;
import com.InstituteManagement.dto.SubmitExamRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping("/admin/exams")
    public ResponseEntity<Exam> createExam(@Valid @RequestBody CreateExamRequest request) {
        return ResponseEntity.ok(examService.createExam(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER')")
    @PostMapping("/admin/exams/questions")
    public ResponseEntity<Question> addQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        return ResponseEntity.ok(examService.addQuestion(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TRAINER','STUDENT')")
    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/students/exams/submit")
    public ResponseEntity<ExamAttempt> submitExam(@Valid @RequestBody SubmitExamRequest request) {
        Long studentId = 1L;
        return ResponseEntity.ok(examService.submitExam(studentId, request));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/students/{studentId}/exam-attempts")
    public ResponseEntity<List<ExamAttempt>> getAttempts(@PathVariable Long studentId) {
        return ResponseEntity.ok(examService.getExamAttemptsForStudent(studentId));
    }
}
