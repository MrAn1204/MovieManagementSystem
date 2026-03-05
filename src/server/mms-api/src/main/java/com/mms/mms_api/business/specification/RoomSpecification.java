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

public class RoomSpecification extends BaseSpecification<Room, RoomSearchQuery> {
    public RoomSpecification(RoomSearchQuery criteria) {
        super(criteria);
    }

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

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        return criteriaBuilder.like(root.get("name"), pattern);
    }

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
}
