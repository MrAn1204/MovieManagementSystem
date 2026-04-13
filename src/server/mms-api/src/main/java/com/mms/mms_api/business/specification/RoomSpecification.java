package com.mms.mms_api.business.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.model.Room;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * JPA specification for filtering and sorting room search results.
 */
public class RoomSpecification extends BaseSpecification<Room, RoomSearchQuery> {
    /**
     * Creates a room specification from the given search criteria.
     *
     * @param criteria the room search criteria
     */
    public RoomSpecification(RoomSearchQuery criteria) {
        super(criteria);
    }

    /**
     * Builds the predicate set for room searches.
     *
     * @param root the root room entity
     * @param query the criteria query being built
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the combined room search predicate
     */
    @Override
    public Predicate toPredicate(@NonNull Root<Room> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(criteria.getKeyword())) {
            predicates.add(buildKeywordPredicate(root, criteriaBuilder));
        }

        if (criteria.getMinCapacity() > 0 || criteria.getMaxCapacity() > 0) {
            predicates.add(buildCapacityPredicate(root, criteriaBuilder));
        }

        applyOrderBy(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    /**
     * Builds a keyword predicate that matches room names.
     *
     * @param root the root room entity
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the keyword predicate for room search
     */
    @Override
    protected Predicate buildKeywordPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        return criteriaBuilder.like(root.get("name"), pattern);
    }

    /**
     * Builds a predicate that filters rooms by computed capacity.
     *
     * @param root the root room entity
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the capacity filter predicate
     */
    private Predicate buildCapacityPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        Expression<Integer> seatQuantityPath = criteriaBuilder.prod(root.get("rowLength"), root.get("columnLength"));

        if (criteria.getMinCapacity() > 0 && criteria.getMaxCapacity() > 0) {
            return criteriaBuilder.between(seatQuantityPath, criteria.getMinCapacity(), criteria.getMaxCapacity());
        } else if (criteria.getMinCapacity() > 0) {
            return criteriaBuilder.greaterThanOrEqualTo(seatQuantityPath, criteria.getMinCapacity());
        } else if (criteria.getMaxCapacity() > 0) {
            return criteriaBuilder.lessThanOrEqualTo(seatQuantityPath, criteria.getMaxCapacity());
        } else {
            return criteriaBuilder.conjunction();
        }
    }

    /**
     * Resolves the sort expression for room searches, including derived capacity fields.
     *
     * @param root the root room entity
     * @param criteriaBuilder the criteria builder used for derived expressions
     * @return the expression used for sorting room results
     */
    @Override
    protected Expression<?> getSortExpression(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        return switch (criteria.getSortBy()) {
            case "currentCapacity" -> criteriaBuilder.size(root.get("seats"));
            case "maxCapacity" -> criteriaBuilder.prod(root.get("rowLength"), root.get("columnLength"));
            default -> root.get(criteria.getSortBy());
        };
    }
}
