package com.mms.mms_api.business.specification;

import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseSpecification<T extends BaseEntity, Q extends BaseSearchQuery> implements Specification<T> {
    protected final Q criteria;

    protected BaseSpecification(Q criteria) {
        this.criteria = criteria;
    }

    protected abstract Predicate buildKeywordPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}
