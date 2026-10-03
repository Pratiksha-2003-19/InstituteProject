package com.InstituteManagement.Controller;

import com.InstituteManagement.Model.InstituteSetting;
import com.InstituteManagement.Service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'STUDENT')")
    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> getSettings() {
        return ResponseEntity.ok(settingsService.getSettingsMap());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/settings")
    public ResponseEntity<List<InstituteSetting>> getAdminSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/settings/{key}")
    public ResponseEntity<InstituteSetting> updateSetting(@PathVariable String key, @RequestBody Map<String, String> payload) {
        String value = payload.getOrDefault("value", "");
        return ResponseEntity.ok(settingsService.updateSetting(key, value));
    }
}
