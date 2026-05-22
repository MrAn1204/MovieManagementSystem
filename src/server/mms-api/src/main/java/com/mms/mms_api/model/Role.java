package com.mms.mms_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing an authorization role.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends AuditableEntity {
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
