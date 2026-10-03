package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Course;
import com.InstituteManagement.Model.User;
import com.InstituteManagement.Repository.BatchRepository;
import com.InstituteManagement.Repository.CourseRepository;
import com.InstituteManagement.Repository.UserRepository;
import com.InstituteManagement.dto.CreateBatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public Batch createBatch(CreateBatchRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start date must be before end date");
        }

        if (request.getMaxStudents() <= 0) {
            throw new RuntimeException("Max students must be positive");
        }

        User trainer = null;
        if (request.getTrainerId() != null) {
            trainer = userRepository.findById(request.getTrainerId())
                    .orElseThrow(() -> new RuntimeException("Trainer not found with id: " + request.getTrainerId()));

            boolean isTrainer = trainer.getRoles().stream()
                    .anyMatch(role -> "TRAINER".equalsIgnoreCase(role.getName()));

            if (!isTrainer) {
                throw new RuntimeException("Selected user is not a trainer");
            }
        }

        Batch batch = new Batch();
        batch.setBatchName(request.getBatchName());
        batch.setCourse(course);
        batch.setStartDate(request.getStartDate());
        batch.setEndDate(request.getEndDate());
        batch.setStartTime(request.getStartTime());
        batch.setEndTime(request.getEndTime());
        batch.setMode(request.getMode());
        batch.setRoom(request.getRoom());
        batch.setMaxStudents(request.getMaxStudents());
        batch.setTrainer(trainer);
        batch.setStatus("UPCOMING");

        return batchRepository.save(batch);
    }

    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    public Batch getBatchById(Long id) {
        return batchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
    }

    public List<Batch> getBatchesByCourseId(Long courseId) {
        return batchRepository.findByCourseId(courseId);
    }
}
