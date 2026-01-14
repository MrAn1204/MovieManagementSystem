package com.mms.mms_api.business.specification;

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
        if (StringUtils.hasText(criteria.getKeyword())) {
            addKeywordPredicate(root, criteriaBuilder);
        }

        if (criteria.getShowTime() != null) {
            addShowTimePredicate(root, criteriaBuilder);
        }

        if (criteria.getMovieId() != null) {
            addMoviePredicate(root, criteriaBuilder);
        }

        if (criteria.getRoomId() != null) {
            addRoomPredicate(root, criteriaBuilder);
        }

        if (criteria.getPromotionId() != null) {
            addPromotionPredicate(root, criteriaBuilder);
        }

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    @Override
    protected void addKeywordPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        String pattern = "%" + criteria.getKeyword().toLowerCase() + "%";

        Join<Ticket, Invoice> invoiceJoin = root.join("invoice");
        Join<Invoice, User> userJoin = invoiceJoin.join("user");

        Predicate usernamePredicate = criteriaBuilder.like(userJoin.get("username"), pattern);
        Predicate phonePredicate = criteriaBuilder.like(userJoin.get("phoneNumber"), pattern);

        predicates.add(criteriaBuilder.or(usernamePredicate, phonePredicate));
    }

    private void addShowTimePredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");

        Predicate showTimePredicate = criteriaBuilder.equal(scheduleJoin.get("showTime"), criteria.getShowTime());

        predicates.add(showTimePredicate);
    }

    private void addMoviePredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
        Join<Schedule, Movie> movieJoin = scheduleJoin.join("movie");

        Predicate moviePredicate = criteriaBuilder.equal(movieJoin.get("id"), criteria.getMovieId());

        predicates.add(moviePredicate);
    }

    private void addRoomPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Schedule> scheduleJoin = root.join("schedule");
        Join<Schedule, Room> roomJoin = scheduleJoin.join("room");

        Predicate roomPredicate = criteriaBuilder.equal(roomJoin.get("id"), criteria.getRoomId());

        predicates.add(roomPredicate);
    }

    private void addPromotionPredicate(Root<Ticket> root, CriteriaBuilder criteriaBuilder) {
        Join<Ticket, Promotion> promotionJoin = root.join("promotion");

        Predicate promotionPredicate = criteriaBuilder.equal(promotionJoin.get("id"), criteria.getPromotionId());

        predicates.add(promotionPredicate);
    }
}
