package com.mms.mms_api.business.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseSpecification<T extends BaseEntity, Q extends BaseSearchQuery> implements Specification<T> {
    protected final Q criteria;

    protected final List<Predicate> predicates = new ArrayList<>();

    protected BaseSpecification(Q criteria) {
        this.criteria = criteria;
    }

    protected abstract void addKeywordPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);
}
