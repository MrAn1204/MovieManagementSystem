package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mms.mms_api.model.Promotion;

/**
 * Repository for promotion persistence and specification queries.
 */
public interface PromotionRepository extends JpaRepository<Promotion, UUID>, JpaSpecificationExecutor<Promotion> {
    
}
