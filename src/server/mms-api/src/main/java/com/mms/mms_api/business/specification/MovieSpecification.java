package com.mms.mms_api.business.specification;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MovieSpecification extends BaseSpecification<Movie, MovieSearchQuery> {
    public MovieSpecification(MovieSearchQuery criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Movie> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        if (StringUtils.hasText(criteria.getKeyword())) {
            addKeywordPredicate(root, criteriaBuilder);
        }

        if (!CollectionUtils.isEmpty(criteria.getGenreIds())) {
            addGenrePredicate(root);
        }

        if (!CollectionUtils.isEmpty(criteria.getStudioIds())) {
            addStudioPredicate(root);
        }

        if (criteria.getLanguageId() != null) {
            addLanguagePredicate(root, criteriaBuilder);
        }

        if (criteria.getReleaseAfter() != null || criteria.getReleaseBefore() != null) {
            addReleaseDatePredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected void addKeywordPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Movie, Talent> talentJoin = root.join("talents");

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), pattern);
        Predicate talentPredicate = criteriaBuilder.like(talentJoin.get("name"), pattern);

        predicates.add(criteriaBuilder.or(namePredicate, talentPredicate));
    }

    private void addGenrePredicate(Root<Movie> root) {
        List<UUID> searchGenres = criteria.getGenreIds();

        Join<Movie, Genre> genreJoin = root.join("genres");

        predicates.add(genreJoin.get("id").in(searchGenres));
    }

    private void addStudioPredicate(Root<Movie> root) {
        List<UUID> searchStudios = criteria.getStudioIds();

        Join<Movie, Studio> studioJoin = root.join("studios");

        predicates.add(studioJoin.get("id").in(searchStudios));
    }

    private void addLanguagePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        UUID searchLanguage = criteria.getLanguageId();

        Join<Movie, Language> languageJoin = root.join("language");

        predicates.add(criteriaBuilder.equal(languageJoin.get("id"), searchLanguage));
    }

    private void addReleaseDatePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        if (criteria.getReleaseAfter() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"), criteria.getReleaseAfter()));
        }

        if (criteria.getReleaseBefore() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("releaseDate"), criteria.getReleaseBefore()));
        }
    }
}
