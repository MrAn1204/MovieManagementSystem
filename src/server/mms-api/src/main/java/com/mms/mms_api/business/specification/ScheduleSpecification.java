package com.mms.mms_api.business.specification;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Schedule;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
        if (StringUtils.hasText(criteria.getKeyword())) {
            addKeywordPredicate(root, criteriaBuilder);
        }

        if (criteria.getDate() != null) {
            addShowTimePredicate(root, criteriaBuilder);
        }

        if (criteria.getRoomId() != null) {
            addRoomPredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected void addKeywordPredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Schedule, Movie> movieJoin = root.join("movie");

        predicates.add(criteriaBuilder.like(movieJoin.get("name"), pattern));
    }

    private void addShowTimePredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        Predicate showTimePredicate;

        Path<LocalDateTime> showTimePath = root.get("showTime");

        if (criteria.getMinTime() != null && criteria.getMaxTime() != null) {
            LocalDateTime fromDateTime = criteria.getDate().atTime(criteria.getMinTime());
            LocalDateTime toDateTime = criteria.getDate().atTime(criteria.getMaxTime());
            showTimePredicate = criteriaBuilder.between(showTimePath, fromDateTime, toDateTime);
        } else {
            LocalDateTime minShowTime = criteria.getMinTime() != null
                    ? criteria.getDate().atTime(criteria.getMinTime())
                    : criteria.getDate().atStartOfDay();
            LocalDateTime endOfDay = criteria.getMaxTime() != null
                    ? criteria.getDate().atTime(criteria.getMaxTime())
                    : criteria.getDate().plusDays(1).atStartOfDay();
            showTimePredicate = criteriaBuilder.between(showTimePath, minShowTime, endOfDay);
        }

        predicates.add(showTimePredicate);
    }

    private void addRoomPredicate(Root<Schedule> root, CriteriaBuilder criteriaBuilder) {
        Join<Schedule, Room> roomJoin = root.join("room");
        
        predicates.add(criteriaBuilder.equal(roomJoin.get("id"), criteria.getRoomId()));
    }
}
