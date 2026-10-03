package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Role;
import com.InstituteManagement.Model.User;
import com.InstituteManagement.Repository.RoleRepository;
import com.InstituteManagement.Repository.UserRepository;
import com.InstituteManagement.dto.CreateTrainerRequest;
import com.InstituteManagement.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createTrainer(CreateTrainerRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Role trainerRole = roleRepository.findByName("TRAINER")
                .orElseGet(() -> roleRepository.save(new Role(null, "TRAINER")));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(User.UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(request.getPassword() == null || request.getPassword().isBlank()
                ? "trainer123" : request.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(trainerRole);
        user.setRoles(roles);

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getStatus().name(),
                saved.getRoles().stream().map(Role::getName).toList()
        );
    }

    public List<UserResponse> getAllTrainers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("TRAINER")))
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getStatus().name(),
                        user.getRoles().stream().map(Role::getName).toList()
                ))
                .toList();
    }
}
