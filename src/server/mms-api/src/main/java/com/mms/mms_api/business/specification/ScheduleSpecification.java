package com.mms.mms_api.business.specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Schedule;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ScheduleSpecification extends BaseSpecification<Schedule, ScheduleSearchQuery> {
    public ScheduleSpecification(ScheduleSearchQuery criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Schedule> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (StringUtils.hasText(criteria.getKeyword())) {
            predicates.add(buildKeywordPredicate(root, criteriaBuilder));
        }

        if (criteria.getDate() != null) {
            predicates.add(buildShowTimePredicate(root, criteriaBuilder));
        }

        if (criteria.getRoomId() != null) {
            predicates.add(buildRoomPredicate(root, criteriaBuilder));
        }

        applyOrderBy(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Schedule, Movie> movieJoin = root.join("movie");

        return criteriaBuilder.like(movieJoin.get("name"), pattern);
    }

    private Predicate buildShowTimePredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        Path<LocalDateTime> showTimePath = root.get("showTime");

        if (criteria.getMinTime() != null && criteria.getMaxTime() != null) {
            LocalDateTime fromDateTime = criteria.getDate().atTime(criteria.getMinTime());
            LocalDateTime toDateTime = criteria.getDate().atTime(criteria.getMaxTime());
            return criteriaBuilder.between(showTimePath, fromDateTime, toDateTime);
        } else {
            LocalDateTime minShowTime = criteria.getMinTime() != null
                    ? criteria.getDate().atTime(criteria.getMinTime())
                    : criteria.getDate().atStartOfDay();
            LocalDateTime endOfDay = criteria.getMaxTime() != null
                    ? criteria.getDate().atTime(criteria.getMaxTime())
                    : criteria.getDate().plusDays(1).atStartOfDay();
            return criteriaBuilder.between(showTimePath, minShowTime, endOfDay);
        }
    }

    private Predicate buildRoomPredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        Join<Schedule, Room> roomJoin = root.join("room");
        
        return criteriaBuilder.equal(roomJoin.get("id"), criteria.getRoomId());
    }

    @Override
    protected Expression<?> getSortExpression(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        return switch (criteria.getSortBy()) {
            case "movieName" -> root.join("movie").get("name");
            case "roomName" -> root.join("room").get("name");
            default -> root.get(criteria.getSortBy());
        };
    }
}
