package com.InstituteManagement.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateBatchRequest {

    @NotBlank(message = "Batch name is required")
    private String batchName;

    @NotNull(message = "Course id is required")
    private Long courseId;

    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotBlank(message = "Mode is required")
    private String mode;

    @NotBlank(message = "Room is required")
    private String room;

    private Long trainerId;

    @NotNull(message = "Max students is required")
    @Positive(message = "Max students must be positive")
    private Integer maxStudents;
}
