package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mms.mms_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
