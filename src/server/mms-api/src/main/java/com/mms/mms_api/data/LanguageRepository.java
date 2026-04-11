package com.mms.mms_api.data;

import com.mms.mms_api.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for language lookup and persistence.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Language findByName(String name);
}