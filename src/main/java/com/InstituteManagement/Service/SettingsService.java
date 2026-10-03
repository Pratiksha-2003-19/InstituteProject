package com.InstituteManagement.Service;

import com.InstituteManagement.Model.InstituteSetting;
import com.InstituteManagement.Repository.InstituteSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final InstituteSettingRepository instituteSettingRepository;

    public List<InstituteSetting> getAllSettings() {
        return instituteSettingRepository.findAll();
    }

    public Map<String, Object> getSettingsMap() {
        Map<String, Object> settings = new LinkedHashMap<>();
        for (InstituteSetting setting : instituteSettingRepository.findAll()) {
            settings.put(setting.getKey(), setting.getValue());
        }
        return settings;
    }

    public InstituteSetting updateSetting(String key, String value) {
        InstituteSetting setting = instituteSettingRepository.findByKey(key)
                .orElseGet(() -> {
                    InstituteSetting newSetting = new InstituteSetting();
                    newSetting.setKey(key);
                    newSetting.setValue(value);
                    newSetting.setDescription("Custom setting");
                    return newSetting;
                });

        setting.setValue(value);
        setting.setUpdatedAt(LocalDateTime.now());
        return instituteSettingRepository.save(setting);
    }
}
