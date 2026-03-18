package com.mms.mms_api.business.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(criteria.getKeyword())) {
            predicates.add(buildKeywordPredicate(root, criteriaBuilder));
        }

        if (criteria.getStartDate() != null || criteria.getEndDate() != null) {
            predicates.add(buildDatePredicate(root, criteriaBuilder));
        }

        applyOrderBy(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<Promotion> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Predicate titlePredicate = criteriaBuilder.like(root.get("title"), pattern);
        Predicate descriptionPredicate = criteriaBuilder.like(root.get("description"), pattern);

        return criteriaBuilder.or(titlePredicate, descriptionPredicate);
    }

    private Predicate buildDatePredicate(Root<Promotion> root, CriteriaBuilder criteriaBuilder) {
        LocalDate searchStart = criteria.getStartDate();
        LocalDate searchEnd = criteria.getEndDate();

        if (searchStart != null && searchEnd != null) {
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchStart);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchEnd);

            return criteriaBuilder.and(startPredicate, endPredicate);
        } else if (searchStart != null) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchStart);
        } else if (searchEnd != null) {
            return criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchEnd);
        } else {
            return criteriaBuilder.conjunction();
        }
    }
}
