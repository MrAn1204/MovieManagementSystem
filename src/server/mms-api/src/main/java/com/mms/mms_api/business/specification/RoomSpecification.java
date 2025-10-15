package com.mms.mms_api.business.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.model.Room;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RoomSpecification implements Specification<Room> {
    private final RoomSearchQuery criteria;

    public RoomSpecification(RoomSearchQuery criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Room> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        Path<Integer> seatQuantityPath = root.get("seatQuantity");

        if (criteria.getSeatQuantityMin() > 0 && criteria.getSeatQuantityMax() > 0) {
            return criteriaBuilder.between(seatQuantityPath, criteria.getSeatQuantityMin(), criteria.getSeatQuantityMax());
        } else if (criteria.getSeatQuantityMin() > 0) {
            return criteriaBuilder.greaterThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMin());
        } else if (criteria.getSeatQuantityMax() > 0) {
            return criteriaBuilder.lessThanOrEqualTo(seatQuantityPath, criteria.getSeatQuantityMax());
        }

        return criteriaBuilder.conjunction();
    }

}
