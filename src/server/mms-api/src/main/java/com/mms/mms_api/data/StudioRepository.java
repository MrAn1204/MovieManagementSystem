package com.mms.mms_api.data;

import com.mms.mms_api.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<Studio, UUID> {
    List<Studio> findByNameIn(List<String> names);
}