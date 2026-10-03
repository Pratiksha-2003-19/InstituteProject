package com.InstituteManagement.Service;

import com.InstituteManagement.Model.*;
import com.InstituteManagement.Repository.*;
import com.InstituteManagement.dto.CreateExamRequest;
import com.InstituteManagement.dto.CreateQuestionRequest;
import com.InstituteManagement.dto.SubmitExamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final BatchRepository batchRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public Exam createExam(CreateExamRequest request) {
        Batch batch = batchRepository.findById(request.getBatchId())
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        if (request.getEndAt().isBefore(request.getStartAt()) || request.getEndAt().isEqual(request.getStartAt())) {
            throw new RuntimeException("Exam end time must be after start time");
        }

        if (request.getPassingMarks() > request.getTotalMarks()) {
            throw new RuntimeException("Passing marks cannot be greater than total marks");
        }

        Exam exam = new Exam();
        exam.setBatch(batch);
        exam.setCourse(course);
        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());
        exam.setStartAt(request.getStartAt());
        exam.setEndAt(request.getEndAt());
        exam.setStatus("ACTIVE");

        return examRepository.save(exam);
    }

    public Question addQuestion(CreateQuestionRequest request) {
        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + request.getExamId()));

        Question question = new Question();
        question.setExam(exam);
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectOption(request.getCorrectOption());
        question.setMarks(request.getMarks());

        return questionRepository.save(question);
    }

    public List<Question> getQuestionsForExam(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public ExamAttempt submitExam(Long studentId, SubmitExamRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + request.getExamId()));

        if (examAttemptRepository.existsByExamIdAndStudentId(exam.getId(), student.getId())) {
            throw new RuntimeException("Student has already attempted this exam");
        }

        List<Question> questions = questionRepository.findByExamId(exam.getId());
        if (questions.isEmpty()) {
            throw new RuntimeException("Exam has no questions configured");
        }

        Map<Long, String> answers = request.getAnswers();
        int score = 0;
        for (Question question : questions) {
            String selected = answers.get(question.getId());
            if (selected != null && selected.equalsIgnoreCase(question.getCorrectOption())) {
                score += question.getMarks();
            }
        }

        int totalMarks = questions.stream().mapToInt(Question::getMarks).sum();
        boolean passed = score >= exam.getPassingMarks();

        ExamAttempt attempt = new ExamAttempt();
        attempt.setExam(exam);
        attempt.setStudent(student);
        attempt.setAttemptNumber(1);
        attempt.setAnswers(answers.toString());
        attempt.setScore(score);
        attempt.setPassed(passed);
        attempt.setStatus("SUBMITTED");
        attempt.setSubmittedAt(LocalDateTime.now());

        return examAttemptRepository.save(attempt);
    }

    public List<ExamAttempt> getExamAttemptsForStudent(Long studentId) {
        return examAttemptRepository.findByStudentId(studentId);
    }
}
