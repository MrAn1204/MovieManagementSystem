package com.mms.mms_api.business.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.model.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MovieSpecification implements Specification<Movie> {
    private final MovieSearchQuery criteria;

    public MovieSpecification(MovieSearchQuery criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Movie> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(criteria.getKeyword())) {
            Predicate keywordPredicate = addKeywordPredicate(root, criteriaBuilder);

            predicates.add(keywordPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate addKeywordPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        return criteriaBuilder.like(root.get("name"), pattern);
    }
}
