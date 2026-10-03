package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Batch;
import com.InstituteManagement.Model.Course;
import com.InstituteManagement.Model.FeeStructure;
import com.InstituteManagement.Repository.BatchRepository;
import com.InstituteManagement.Repository.CourseRepository;
import com.InstituteManagement.Repository.FeeStructureRepository;
import com.InstituteManagement.dto.CreateFeeStructureRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;
    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;

    public FeeStructure createFeeStructure(CreateFeeStructureRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + request.getCourseId()));

        Batch batch = null;
        if (request.getBatchId() != null) {
            batch = batchRepository.findById(request.getBatchId())
                    .orElseThrow(() -> new RuntimeException("Batch not found with id: " + request.getBatchId()));
        }

        if (request.getTotalFee().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Total fee must be greater than zero");
        }

        if (request.getInstallmentCount() <= 0) {
            throw new RuntimeException("Installment count must be positive");
        }

        BigDecimal installmentAmount = request.getTotalFee().divide(BigDecimal.valueOf(request.getInstallmentCount()), 2, java.math.RoundingMode.HALF_UP);

        FeeStructure feeStructure = new FeeStructure();
        feeStructure.setCourse(course);
        feeStructure.setBatch(batch);
        feeStructure.setTotalFee(request.getTotalFee());
        feeStructure.setInstallmentCount(request.getInstallmentCount());
        feeStructure.setInstallmentAmount(installmentAmount);
        feeStructure.setDueDate(request.getDueDate());
        feeStructure.setStatus("ACTIVE");

        return feeStructureRepository.save(feeStructure);
    }

    public List<FeeStructure> getAllFeeStructures() {
        return feeStructureRepository.findAll();
    }
}
