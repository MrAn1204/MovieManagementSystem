package com.mms.mms_api.business.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
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

        if (!CollectionUtils.isEmpty(criteria.getGenres())) {
            Predicate genrePredicate = addGenrePredicate(root);

            predicates.add(genrePredicate);
        }

        if (StringUtils.hasText(criteria.getLanguage())) {
            Predicate languagePredicate = addLanguagePredicate(root, criteriaBuilder);

            predicates.add(languagePredicate);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private Predicate addKeywordPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Movie, Studio> studioJoin = root.join("studios");
        Join<Movie, Talent> talentJoin = root.join("talents");

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), pattern);
        Predicate studioPredicate = criteriaBuilder.like(studioJoin.get("name"), pattern);
        Predicate talentPredicate = criteriaBuilder.like(talentJoin.get("name"), pattern);

        return criteriaBuilder.or(namePredicate, studioPredicate, talentPredicate);
    }

    private Predicate addGenrePredicate(Root<Movie> root) {
        List<String> searchGenres = criteria.getGenres().stream()
                .map(genre -> StringUtils.capitalize(genre.toLowerCase())).toList();

        Join<Movie, Genre> genreJoin = root.join("genres");

        return genreJoin.get("name").in(searchGenres);
    }

    private Predicate addLanguagePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = criteria.getLanguage().toUpperCase();

        Join<Movie, Language> languageJoin = root.join("language");

        return criteriaBuilder.equal(languageJoin.get("name"), pattern);
    }
}
