package com.InstituteManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserStatusRequest {

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|PENDING_VERIFICATION|BLOCKED", message = "Status must be ACTIVE, PENDING_VERIFICATION, or BLOCKED")
    private String status;
}
