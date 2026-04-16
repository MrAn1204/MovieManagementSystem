package com.mms.mms_api.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mms.mms_api.model.Ticket;

/**
 * Repository for ticket persistence and specification queries.
 */
public interface TicketRepository extends JpaRepository<Ticket, UUID>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> findByIdIn(List<UUID> ids);

    int countByIdIn(Iterable<UUID> ids);

    /**
     * Checks whether all provided ticket identifiers exist.
     */
    default boolean existsAllByIdIn(List<UUID> ids) {
        return countByIdIn(ids) == ids.size();
    }
}
