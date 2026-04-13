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

/**
 * Base JPA specification implementation for searchable entities.
 *
 * @param <T> the entity type being queried
 * @param <Q> the search query type providing filter and sort criteria
 */
public abstract class BaseSpecification<T extends BaseEntity, Q extends BaseSearchQuery> implements Specification<T> {
    protected final Q criteria;

    /**
     * Creates a specification backed by the given search criteria.
     *
     * @param criteria the search criteria used to build predicates and ordering
     */
    protected BaseSpecification(Q criteria) {
        this.criteria = criteria;
    }

    /**
     * Builds the keyword predicate for the current entity type.
     *
     * @param root the root entity in the criteria query
     * @param criteriaBuilder the criteria builder used to create the predicate
     * @return the predicate used for keyword matching
     */
    protected abstract Predicate buildKeywordPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

    /**
     * Applies sorting from the search criteria to the current query.
     *
     * @param root the root entity in the criteria query
     * @param query the criteria query to update, or {@code null} if unavailable
     * @param criteriaBuilder the criteria builder used to create the order expression
     */
    protected void applyOrderBy(Root<T> root, @Nullable CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        if (query == null) {
            return;
        }

        Expression<?> sortExpression = getSortExpression(root, criteriaBuilder);

        query.orderBy(criteria.getSortDirection().isAscending() ? criteriaBuilder.asc(sortExpression)
                : criteriaBuilder.desc(sortExpression));
    }

    /**
     * Resolves the expression used for sorting.
     *
     * @param root the root entity in the criteria query
     * @param criteriaBuilder the criteria builder used for derived expressions
     * @return the expression to use in the {@code order by} clause
     */
    protected Expression<?> getSortExpression(Root<T> root, CriteriaBuilder criteriaBuilder) {
        return root.get(criteria.getSortBy());
    }
}
