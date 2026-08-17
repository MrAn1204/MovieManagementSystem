package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.util.CurrentUserHelper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.seat.SeatDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

/**
 * Handles requests to retrieve seat by id.
 */
@Component
public class SeatGetByIdHandler extends SeatBaseHandler<SeatGetByIdQuery, SeatDetailDto> {
    private final CurrentUserHelper currentUser;

    /**
     * Creates a SeatGetByIdHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     * @param currentUserHelper current user helper
     */
    public SeatGetByIdHandler(SeatMapper seatMapper, SeatRepository seatRepository, CurrentUserHelper currentUserHelper) {
        super(seatMapper, seatRepository);
        this.currentUser = currentUserHelper;
    }

    /**
     * Retrieves a seat by its identifier.
     *
     * @param request query containing the target seat id
     * @return seat detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the seat does not exist
     */
    @Override
    public SeatDetailDto execute(SeatGetByIdQuery request) {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        SeatDetailDto dto = seatMapper.toDetailDto(seat);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(seat));
        }

        return dto;
    }
}