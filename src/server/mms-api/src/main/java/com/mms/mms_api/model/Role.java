package com.mms.mms_api.model;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
