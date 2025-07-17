package com.mms.mms_api.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.mms_api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByNameIn(List<String> names);
}
