package com.InstituteManagement.Repository;

import com.InstituteManagement.Model.InstituteSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteSettingRepository extends JpaRepository<InstituteSetting, Long> {
    Optional<InstituteSetting> findByKey(String key);
}
