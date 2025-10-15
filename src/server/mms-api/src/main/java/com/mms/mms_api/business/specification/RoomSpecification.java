package com.mms.mms_api.business.specification;

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
        if (StringUtils.hasText(criteria.getKeyword())) {
            addKeywordPredicate(root, criteriaBuilder);
        }

        if (criteria.getSeatQuantityMin() > 0 || criteria.getSeatQuantityMax() > 0) {
            addSeatQuantityPredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private void addSeatQuantityPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        Predicate seatQuantityPredicate = criteriaBuilder.conjunction();
        
        Path<Integer> seatQuantityPath = root.get("seatQuantity");

        if (criteria.getSeatQuantityMin() > 0 && criteria.getSeatQuantityMax() > 0) {
            seatQuantityPredicate = criteriaBuilder.between(seatQuantityPath, criteria.getSeatQuantityMin(), criteria.getSeatQuantityMax());
        } else if (criteria.getSeatQuantityMin() > 0) {
            seatQuantityPredicate = criteriaBuilder.greaterThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMin());
        } else if (criteria.getSeatQuantityMax() > 0) {
            seatQuantityPredicate = criteriaBuilder.lessThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMax());
        }

        predicates.add(seatQuantityPredicate);
    }

    @Override
    protected void addKeywordPredicate(Root<Room> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        predicates.add(criteriaBuilder.like(root.get("name"), pattern));
    }

}
