package com.InstituteManagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateFeeStructureRequest {

    @NotNull(message = "Course id is required")
    private Long courseId;

    private Long batchId;

    @NotNull(message = "Total fee is required")
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal totalFee;

    @NotNull(message = "Installment count is required")
    @Positive(message = "Installment count must be positive")
    private Integer installmentCount;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}
