package com.mms.mms_api.business.specification;

import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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

        addKeywordPredicate(root, criteriaBuilder, predicates);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private void addKeywordPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (StringUtils.hasText(criteria.getKeyword())) {
            String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullname")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern)));
        }
    }
}