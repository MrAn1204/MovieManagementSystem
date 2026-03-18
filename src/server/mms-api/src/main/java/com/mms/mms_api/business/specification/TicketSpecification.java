package com.mms.mms_api.business.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketSpecification extends BaseSpecification<Ticket, TicketSearchQuery> {
    public TicketSpecification(TicketSearchQuery criteria) {
        super(criteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Ticket> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (StringUtils.hasText(criteria.getKeyword())) {
            buildKeywordPredicate(root, criteriaBuilder);
        }

        if (criteria.getShowTime() != null) {
            predicates.add(buildShowTimePredicate(root, criteriaBuilder));
        }

        if (criteria.getMovieId() != null) {
            predicates.add(buildMoviePredicate(root, criteriaBuilder));
        }

        if (criteria.getRoomId() != null) {
            predicates.add(buildRoomPredicate(root, criteriaBuilder));
        }

        if (criteria.getPromotionId() != null) {
            predicates.add(buildPromotionPredicate(root, criteriaBuilder));
        }

        applyOrderBy(root, query, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected Predicate buildKeywordPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Ticket, Invoice> invoiceJoin = root.join("invoice");
        Join<Invoice, User> userJoin = invoiceJoin.join("user");

        Predicate usernamePredicate = criteriaBuilder.like(userJoin.get("username"), pattern);
        Predicate phonePredicate = criteriaBuilder.like(userJoin.get("phoneNumber"), pattern);

        return criteriaBuilder.or(usernamePredicate, phonePredicate);
    }

    private Predicate buildShowTimePredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");

        return criteriaBuilder.equal(scheduleJoin.get("showTime"), criteria.getShowTime());
    }

    private Predicate buildMoviePredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
        Join<Schedule, Movie> movieJoin = scheduleJoin.join("movie");

        return criteriaBuilder.equal(movieJoin.get("id"), criteria.getMovieId());
    }

    private Predicate buildRoomPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
        Join<Schedule, Room> roomJoin = scheduleJoin.join("room");

        return criteriaBuilder.equal(roomJoin.get("id"), criteria.getRoomId());
    }

    private Predicate buildPromotionPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Promotion> promotionJoin = root.join("promotion");

        return criteriaBuilder.equal(promotionJoin.get("id"), criteria.getPromotionId());
    }
}
