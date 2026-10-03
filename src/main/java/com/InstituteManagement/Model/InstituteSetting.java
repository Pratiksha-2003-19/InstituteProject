package com.InstituteManagement.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "institute_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstituteSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key", nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String value;

    private String description;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
