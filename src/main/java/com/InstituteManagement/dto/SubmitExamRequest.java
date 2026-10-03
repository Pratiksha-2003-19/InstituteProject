package com.InstituteManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class SubmitExamRequest {

    @NotNull(message = "Exam id is required")
    private Long examId;

    @NotNull(message = "Answers are required")
    private Map<Long, String> answers;
}
