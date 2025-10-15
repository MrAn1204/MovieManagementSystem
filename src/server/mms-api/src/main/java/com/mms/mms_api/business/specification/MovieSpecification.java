package com.mms.mms_api.business.specification;

import java.util.List;

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

        if (!CollectionUtils.isEmpty(criteria.getGenres())) {
            addGenrePredicate(root);
        }

        if (StringUtils.hasText(criteria.getLanguage())) {
            addLanguagePredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected void addKeywordPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Movie, Studio> studioJoin = root.join("studios");
        Join<Movie, Talent> talentJoin = root.join("talents");

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), pattern);
        Predicate studioPredicate = criteriaBuilder.like(studioJoin.get("name"), pattern);
        Predicate talentPredicate = criteriaBuilder.like(talentJoin.get("name"), pattern);

        predicates.add(criteriaBuilder.or(namePredicate, studioPredicate, talentPredicate));
    }

    private void addGenrePredicate(Root<Movie> root) {
        List<String> searchGenres = criteria.getGenres().stream()
                .map(genre -> StringUtils.capitalize(genre.toLowerCase())).toList();

        Join<Movie, Genre> genreJoin = root.join("genres");

        predicates.add(genreJoin.get("name").in(searchGenres));
    }

    private void addLanguagePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = criteria.getLanguage().toUpperCase();

        Join<Movie, Language> languageJoin = root.join("language");

        predicates.add(criteriaBuilder.equal(languageJoin.get("name"), pattern));
    }
}
