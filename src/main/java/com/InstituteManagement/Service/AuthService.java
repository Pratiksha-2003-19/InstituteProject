package com.InstituteManagement.Service;

import com.InstituteManagement.Model.Role;
import com.InstituteManagement.Model.User;
import com.InstituteManagement.Repository.RoleRepository;
import com.InstituteManagement.Repository.UserRepository;
import com.InstituteManagement.dto.LoginRequest;
import com.InstituteManagement.dto.LoginResponse;
import com.InstituteManagement.dto.RegisterRequest;

import com.InstituteManagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }


        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new RuntimeException("STUDENT role not found — seed it first"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(studentRole));

        return userRepository.save(user);

    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("STUDENT");
        
        String token = jwtUtil.generateToken(user.getEmail(), role);
        return new LoginResponse(token, user.getEmail(), role);
    }
}