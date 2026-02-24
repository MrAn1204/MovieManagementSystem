package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.TicketRepository;

import lombok.AllArgsConstructor;

@Service("ticketValidationService")
@AllArgsConstructor
public class TicketValidationService {
    private final TicketRepository ticketRepository;

    public boolean existsAllByIdIn(List<UUID> ids) {
        return ticketRepository.existsAllByIdIn(ids);
    }

    public boolean isOwnedByUserId(UUID ticketId, UUID userId) {
        if (ticketId == null || userId == null) {
            return false;
        }

        return ticketRepository.findById(ticketId)
                .map(ticket -> ticket.getUser().getId().equals(userId))
                .orElse(false);
    }
}
