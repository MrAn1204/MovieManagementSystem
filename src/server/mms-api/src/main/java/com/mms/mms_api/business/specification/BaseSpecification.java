package com.mms.mms_api.business.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class BaseSpecification<T extends BaseEntity, Q extends BaseSearchQuery> implements Specification<T> {
    protected final Q criteria;

    protected BaseSpecification(Q criteria) {
        this.criteria = criteria;
    }

    protected abstract Predicate buildKeywordPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

    protected void applyOrderBy(Root<T> root, @Nullable CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            return;
        }

        Expression<?> sortExpression = getSortExpression(root, criteriaBuilder);

        query.orderBy(criteria.getSortDirection().isAscending() ? criteriaBuilder.asc(sortExpression)
                : criteriaBuilder.desc(sortExpression));
    }

    protected Expression<?> getSortExpression(Root<T> root, CriteriaBuilder criteriaBuilder) {
        return root.get(criteria.getSortBy());
    }
}
