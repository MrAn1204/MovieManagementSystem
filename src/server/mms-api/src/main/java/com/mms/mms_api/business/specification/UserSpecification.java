package com.mms.mms_api.business.specification;

import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {
    private final UserSearchQuery criteria;

    public UserSpecification(UserSearchQuery criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(criteria.getKeyword())) {
            Predicate keywordPredicate = addKeywordPredicate(root, criteriaBuilder);

            predicates.add(keywordPredicate);
        }

        if (StringUtils.hasText(criteria.getRole())) {
            Predicate rolePredicate = addRolePredicate(root, criteriaBuilder);

            predicates.add(rolePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate addKeywordPredicate(Root<User> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Predicate usernamePredicate = criteriaBuilder.like(root.get("username"), pattern);
        Predicate fullnamePredicate = criteriaBuilder.like(root.get("fullname"), pattern);
        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), pattern);

        return criteriaBuilder.or(usernamePredicate, fullnamePredicate, emailPredicate);
    }

    private Predicate addRolePredicate(Root<User> root, CriteriaBuilder criteriaBuilder) {
        String searchRole = criteria.getRole().toUpperCase();

        Join<User, Role> roleJoin = root.join("roles");

        return criteriaBuilder.equal(roleJoin.get("name"), searchRole);
    }
}