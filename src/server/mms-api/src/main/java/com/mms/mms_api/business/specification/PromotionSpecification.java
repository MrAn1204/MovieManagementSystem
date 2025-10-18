package com.mms.mms_api.business.specification;

import java.time.LocalDate;

import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.model.Promotion;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class PromotionSpecification extends BaseSpecification<Promotion, PromotionSearchQuery> {
    public PromotionSpecification(PromotionSearchQuery criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Promotion> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasText(criteria.getKeyword())) {
            addKeywordPredicate(root, criteriaBuilder);
        }

        if (criteria.getStartDate() != null || criteria.getEndDate() != null) {
            addDatePredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected void addKeywordPredicate(Root<Promotion> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Predicate titlePredicate = criteriaBuilder.like(root.get("title"), pattern);
        Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), pattern);

        predicates.add(criteriaBuilder.or(titlePredicate, descriptionPredicate));
    }

    private void addDatePredicate(Root<Promotion> root, CriteriaBuilder criteriaBuilder) {
        LocalDate searchStart = criteria.getStartDate();
        LocalDate searchEnd = criteria.getEndDate();

        if (searchStart != null && searchEnd != null) {
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchStart);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchEnd);

            predicates.add(criteriaBuilder.and(startPredicate, endPredicate));
        } else if (searchStart != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchStart));
        } else if (searchEnd != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchEnd));
        }
    }
}
