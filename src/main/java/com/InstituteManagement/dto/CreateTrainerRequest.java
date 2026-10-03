package com.InstituteManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTrainerRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String expertise;
    private String qualification;
    private Integer experienceYears;
    private String resumeUrl;
}
