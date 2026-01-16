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
import jakarta.persistence.criteria.Path;
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

        if (criteria.getSeatQuantityMin() > 0 || criteria.getSeatQuantityMax() > 0) {
            predicates.add(buildSeatQuantityPredicate(root, criteriaBuilder));
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        return criteriaBuilder.like(root.get("name"), pattern);
    }

    private Predicate buildSeatQuantityPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        Path<Integer> seatQuantityPath = root.get("seatQuantity");

        if (criteria.getSeatQuantityMin() > 0 && criteria.getSeatQuantityMax() > 0) {
            return criteriaBuilder.between(seatQuantityPath, criteria.getSeatQuantityMin(), criteria.getSeatQuantityMax());
        } else if (criteria.getSeatQuantityMin() > 0) {
            return criteriaBuilder.greaterThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMin());
        } else if (criteria.getSeatQuantityMax() > 0) {
            return criteriaBuilder.lessThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMax());
        } else {
            return criteriaBuilder.conjunction();
        }
    }
}
