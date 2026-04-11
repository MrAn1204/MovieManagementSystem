package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.business.specification.RoomSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Handles room search queries.
 */
@Component
public class RoomSearchHandler extends RoomBaseHandler<RoomSearchQuery, PaginatedResult<RoomDto>> {

    /**
     * Creates a RoomSearchHandler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    public RoomSearchHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    /**
     * Executes a paginated room search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of room DTOs
     */
    @Override
    public PaginatedResult<RoomDto> execute(RoomSearchQuery request) {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<Room> spec = new RoomSpecification(request);

        Page<Room> rooms = roomRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(rooms, roomMapper::toDto);
    }

}
