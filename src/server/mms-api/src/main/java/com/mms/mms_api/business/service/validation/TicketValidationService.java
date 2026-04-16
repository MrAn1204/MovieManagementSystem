package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.TicketRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for ticket existence and ownership checks.
 */
@Service("ticketValidationService")
@AllArgsConstructor
public class TicketValidationService {
    private final TicketRepository ticketRepository;

    /**
     * Checks whether all given ticket ids exist.
     *
     * @param ids ticket identifiers
     * @return true when all identifiers exist
     */
    public boolean existsAllByIdIn(List<UUID> ids) {
        return ticketRepository.existsAllByIdIn(ids);
    }

    /**
     * Checks whether a ticket belongs to a user.
     *
     * @param ticketId ticket identifier
     * @param userId user identifier
     * @return true when owned by the user
     */
    public boolean isOwnedByUserId(UUID ticketId, UUID userId) {
        if (ticketId == null || userId == null) {
            return false;
        }

        return ticketRepository.findById(ticketId)
                .map(ticket -> ticket.getUser().getId().equals(userId))
                .orElse(false);
    }
}
