package com.InstituteManagement.config;

import com.InstituteManagement.Model.*;
import com.InstituteManagement.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final BatchRepository batchRepository;
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;
    private final ExamRepository examRepository;
    private final CertificateRepository certificateRepository;
    private final NotificationRepository notificationRepository;
    private final InstituteSettingRepository instituteSettingRepository;

    @Override
    public void run(String... args) {
        cleanupLegacyRoles();
        seedRole("ADMIN");
        seedRole("TRAINER");
        seedRole("STUDENT");

        seedCourses();
        seedSampleData();
    }

    private void cleanupLegacyRoles() {
        List<Role> allRoles = roleRepository.findAll();
        for (Role role : allRoles) {
            if (role.getName() == null) {
                roleRepository.delete(role);
            }
        }

        roleRepository.findByName("INSTRUCTOR").ifPresent(roleRepository::delete);
    }

    private void seedRole(String roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
    }

    private void seedCourses() {
        if (courseRepository.count() > 0) return; // don't duplicate

        Course c1 = new Course();
        c1.setTitle("Java Full Stack");
        c1.setDescription("Core Java, Spring Boot, JDBC, and frontend basics.");
        c1.setCategory("IT");
        c1.setDurationInHours(120);
        c1.setFee(new BigDecimal("25000"));
        c1.setLevel("BEGINNER");
        c1.setStatus("ACTIVE");
        c1.setCreatedAt(LocalDateTime.now());

        Course c2 = new Course();
        c2.setTitle("Web Development (React + Node)");
        c2.setDescription("Frontend with React and backend with Node.js and Express.");
        c2.setCategory("IT");
        c2.setDurationInHours(100);
        c2.setFee(new BigDecimal("20000"));
        c2.setLevel("BEGINNER");
        c2.setStatus("ACTIVE");
        c2.setCreatedAt(LocalDateTime.now());

        Course c3 = new Course();
        c3.setTitle("Data Structures & Algorithms");
        c3.setDescription("DSA fundamentals with problem solving in Java.");
        c3.setCategory("IT");
        c3.setDurationInHours(80);
        c3.setFee(new BigDecimal("15000"));
        c3.setLevel("INTERMEDIATE");
        c3.setStatus("ACTIVE");
        c3.setCreatedAt(LocalDateTime.now());

        courseRepository.saveAll(List.of(c1, c2, c3));
    }

    private void seedSampleData() {
        if (studentRepository.count() > 0 || batchRepository.count() > 0) {
            seedSettings();
            return;
        }

        Course course = courseRepository.findAll().stream().findFirst().orElseThrow();
        Role trainerRole = roleRepository.findByName("TRAINER").orElseThrow();
        Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();

        User admin = new User();
        admin.setFullName("Institute Admin");
        admin.setEmail("admin@institute.com");
        admin.setPassword("$2a$10$1H0rJm7P9yA0xCzMSJvH1uW3X0K6aFv3P8nVwzXYvS9a0Odbm3g.2");
        admin.setPhone("9999999999");
        admin.setStatus(User.UserStatus.ACTIVE);
        admin.setRoles(new HashSet<>(List.of(adminRole)));
        admin = userRepository.save(admin);

        User trainer = new User();
        trainer.setFullName("Jane Trainer");
        trainer.setEmail("trainer@institute.com");
        trainer.setPassword("$2a$10$1H0rJm7P9yA0xCzMSJvH1uW3X0K6aFv3P8nVwzXYvS9a0Odbm3g.2");
        trainer.setPhone("8888888888");
        trainer.setStatus(User.UserStatus.ACTIVE);
        trainer.setRoles(new HashSet<>(List.of(trainerRole)));
        trainer = userRepository.save(trainer);

        Batch batch = new Batch();
        batch.setBatchName("JAVA-2025-01");
        batch.setCourse(course);
        batch.setStartDate(LocalDate.now().minusDays(15));
        batch.setEndDate(LocalDate.now().plusDays(45));
        batch.setStartTime(java.time.LocalTime.of(9, 30));
        batch.setEndTime(java.time.LocalTime.of(12, 30));
        batch.setMode("ONLINE");
        batch.setRoom("A-101");
        batch.setMaxStudents(25);
        batch.setStatus("ACTIVE");
        batch.setTrainer(trainer);
        batch = batchRepository.save(batch);

        Student student1 = new Student();
        student1.setFullName("Asha Verma");
        student1.setEmail("asha@student.com");
        student1.setPhone("7410001111");
        student1.setBatch(batch);
        student1.setStatus("ACTIVE");

        Student student2 = new Student();
        student2.setFullName("Rohit Sharma");
        student2.setEmail("rohit@student.com");
        student2.setPhone("7410002222");
        student2.setBatch(batch);
        student2.setStatus("ACTIVE");

        studentRepository.saveAll(List.of(student1, student2));

        Payment payment1 = new Payment();
        payment1.setStudent(student1);
        payment1.setBatch(batch);
        payment1.setCourse(course);
        payment1.setAmount(new BigDecimal("18500.00"));
        payment1.setPaymentMethod("UPI");
        payment1.setStatus("PAID");
        payment1.setTransactionRef("TXN-1001");
        payment1.setPaidAt(LocalDateTime.now().minusDays(4));
        paymentRepository.save(payment1);

        Payment payment2 = new Payment();
        payment2.setStudent(student2);
        payment2.setBatch(batch);
        payment2.setCourse(course);
        payment2.setAmount(new BigDecimal("9500.00"));
        payment2.setPaymentMethod("CARD");
        payment2.setStatus("PENDING");
        payment2.setTransactionRef("TXN-1002");
        payment2.setPaidAt(LocalDateTime.now().minusDays(1));
        paymentRepository.save(payment2);

        Attendance attendance1 = new Attendance();
        attendance1.setStudent(student1);
        attendance1.setBatch(batch);
        attendance1.setAttendanceDate(LocalDate.now().minusDays(1));
        attendance1.setStatus("PRESENT");
        attendance1.setRemarks("Attended all sessions");
        attendanceRepository.save(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.setStudent(student2);
        attendance2.setBatch(batch);
        attendance2.setAttendanceDate(LocalDate.now().minusDays(1));
        attendance2.setStatus("ABSENT");
        attendance2.setRemarks("Medical leave");
        attendanceRepository.save(attendance2);

        Exam exam = new Exam();
        exam.setBatch(batch);
        exam.setCourse(course);
        exam.setTitle("Java Fundamentals Assessment");
        exam.setDescription("Core Java concepts and OOP evaluation.");
        exam.setDurationMinutes(90);
        exam.setTotalMarks(100);
        exam.setPassingMarks(40);
        exam.setStartAt(LocalDateTime.now().plusDays(2));
        exam.setEndAt(LocalDateTime.now().plusDays(2).plusMinutes(90));
        exam.setStatus("ACTIVE");
        examRepository.save(exam);

        Certificate certificate = new Certificate();
        certificate.setStudent(student1);
        certificate.setCourse(course);
        certificate.setCertificateNumber("CERT-2025-001");
        certificate.setGrade("A");
        certificate.setStatus("ISSUED");
        certificate.setIssuedAt(LocalDateTime.now().minusDays(7));
        certificate.setPdfUrl("/api/certificates/CERT-2025-001/download");
        certificate.setQrCodeData("CERT=CERT-2025-001");
        certificateRepository.save(certificate);

        Notification notification = new Notification();
        notification.setUser(admin);
        notification.setTitle("Fee payment reminder");
        notification.setMessage("Student Rohit Sharma has a pending fee installment for the Java batch.");
        notification.setType("INFO");
        notification.setRead(false);
        notificationRepository.save(notification);

        seedSettings();
    }

    private void seedSettings() {
        if (instituteSettingRepository.count() > 0) return;

        InstituteSetting[] defaults = new InstituteSetting[] {
                new InstituteSetting(null, "instituteName", "Prime Academy", "Official institute name", LocalDateTime.now()),
                new InstituteSetting(null, "currency", "INR", "Currency used in payments", LocalDateTime.now()),
                new InstituteSetting(null, "timezone", "Asia/Kolkata", "Default timezone", LocalDateTime.now()),
                new InstituteSetting(null, "notificationEmail", "admin@institute.com", "Admin alert email", LocalDateTime.now())
        };

        instituteSettingRepository.saveAll(List.of(defaults));
    }
}
