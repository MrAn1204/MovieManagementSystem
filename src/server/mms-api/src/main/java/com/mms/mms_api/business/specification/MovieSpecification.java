package com.mms.mms_api.business.specification;

import java.time.LocalDate;
import java.util.ArrayList;
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
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * JPA specification for filtering and sorting movie search results.
 */
public class MovieSpecification extends BaseSpecification<Movie, MovieSearchQuery> {
    /**
     * Creates a movie specification from the given search criteria.
     *
     * @param criteria the movie search criteria
     */
    public MovieSpecification(MovieSearchQuery criteria) {
        super(criteria);
    }

    /**
     * Builds the predicate set for movie searches.
     *
     * @param root the root movie entity
     * @param query the criteria query being built
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the combined movie search predicate
     */
    @Override
    public Predicate toPredicate(@NonNull Root<Movie> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (StringUtils.hasText(criteria.getKeyword())) {
            predicates.add(buildKeywordPredicate(root, criteriaBuilder));
        }

        if (!CollectionUtils.isEmpty(criteria.getGenreIds())) {
            predicates.add(buildGenrePredicate(root));
        }

        if (!CollectionUtils.isEmpty(criteria.getStudioIds())) {
            predicates.add(buildStudioPredicate(root));
        }

        if (criteria.getLanguageId() != null) {
            predicates.add(buildLanguagePredicate(root, criteriaBuilder));
        }

        if (criteria.getReleaseAfter() != null || criteria.getReleaseBefore() != null) {
            predicates.add(buildReleaseDatePredicate(root, criteriaBuilder));
        }

        applyOrderBy(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    /**
     * Builds a keyword predicate that matches movie names and talent names.
     *
     * @param root the root movie entity
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the keyword predicate for movie search
     */
    @Override
    protected Predicate buildKeywordPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Movie, Talent> talentJoin = root.join("talents");

        Predicate namePredicate = criteriaBuilder.like(root.get("name"), pattern);
        Predicate talentPredicate = criteriaBuilder.like(talentJoin.get("name"), pattern);

        return criteriaBuilder.or(namePredicate, talentPredicate);
    }

    /**
     * Builds a predicate that filters movies by genre ids.
     *
     * @param root the root movie entity
     * @return the genre filter predicate
     */
    private Predicate buildGenrePredicate(Root<Movie> root) {
        List<UUID> searchGenres = criteria.getGenreIds();

        Join<Movie, Genre> genreJoin = root.join("genres");

        return genreJoin.get("id").in(searchGenres);
    }

    /**
     * Builds a predicate that filters movies by studio ids.
     *
     * @param root the root movie entity
     * @return the studio filter predicate
     */
    private Predicate buildStudioPredicate(Root<Movie> root) {
        List<UUID> searchStudios = criteria.getStudioIds();

        Join<Movie, Studio> studioJoin = root.join("studios");

        return studioJoin.get("id").in(searchStudios);
    }

    /**
     * Builds a predicate that filters movies by language id.
     *
     * @param root the root movie entity
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the language filter predicate
     */
    private Predicate buildLanguagePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        UUID searchLanguage = criteria.getLanguageId();

        Join<Movie, Language> languageJoin = root.join("language");

        return criteriaBuilder.equal(languageJoin.get("id"), searchLanguage);
    }

    /**
     * Builds a predicate that filters movies by release date bounds.
     *
     * @param root the root movie entity
     * @param criteriaBuilder the criteria builder used to create predicates
     * @return the release date filter predicate
     */
    private Predicate buildReleaseDatePredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder) {
        Path<LocalDate> releaseDatePath = root.get("releaseDate");

        if (criteria.getReleaseAfter() != null && criteria.getReleaseBefore() != null) {
            return criteriaBuilder.between(releaseDatePath, criteria.getReleaseAfter(), criteria.getReleaseBefore());
        } else if (criteria.getReleaseAfter() != null) {
            return criteriaBuilder.greaterThanOrEqualTo(releaseDatePath, criteria.getReleaseAfter());
        } else if (criteria.getReleaseBefore() != null) {
            return criteriaBuilder.lessThanOrEqualTo(releaseDatePath, criteria.getReleaseBefore());
        } else {
            return criteriaBuilder.conjunction();
        }
    }
}
