package com.InstituteManagement.Controller;

import com.InstituteManagement.Service.TrainerService;
import com.InstituteManagement.dto.CreateTrainerRequest;
import com.InstituteManagement.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/trainers")
    public ResponseEntity<UserResponse> createTrainer(@Valid @RequestBody CreateTrainerRequest request) {
        return ResponseEntity.ok(trainerService.createTrainer(request));
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<UserResponse>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }
}
