package com.mms.mms_api.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mms.mms_api.data.projection.MonthlyTicketSalesProjection;
import com.mms.mms_api.model.Invoice;

/**
 * Repository for invoice persistence operations.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    @Query("""
            SELECT MONTH(i.createdAt) AS monthNumber, COUNT(t) AS ticketCount
            FROM Invoice i
            JOIN i.tickets t
            WHERE i.createdAt >= :start AND i.createdAt < :end
            GROUP BY MONTH(i.createdAt)
            ORDER BY MONTH(i.createdAt)
            """)
    List<MonthlyTicketSalesProjection> countSoldTicketsByMonth(@Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
