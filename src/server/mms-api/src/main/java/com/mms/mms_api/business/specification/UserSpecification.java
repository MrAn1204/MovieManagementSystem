package com.mms.mms_api.business.specification;

import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class UserSpecification extends BaseSpecification<User, UserSearchQuery> {
    public UserSpecification(UserSearchQuery criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(criteria.getKeyword())) {
            predicates.add(buildKeywordPredicate(root, criteriaBuilder));
        }

        if (criteria.getRoleId() != null) {
            predicates.add(buildRolePredicate(root, criteriaBuilder));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<User> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Predicate usernamePredicate = criteriaBuilder.like(root.get("username"), pattern);
        Predicate fullnamePredicate = criteriaBuilder.like(root.get("fullname"), pattern);
        Predicate emailPredicate = criteriaBuilder.like(root.get("email"), pattern);

        return criteriaBuilder.or(usernamePredicate, fullnamePredicate, emailPredicate);
    }

    private Predicate buildRolePredicate(Root<User> root, CriteriaBuilder criteriaBuilder) {
        UUID searchRole = criteria.getRoleId();

        Join<User, Role> roleJoin = root.join("roles");

        return criteriaBuilder.equal(roleJoin.get("id"), searchRole);
    }
}