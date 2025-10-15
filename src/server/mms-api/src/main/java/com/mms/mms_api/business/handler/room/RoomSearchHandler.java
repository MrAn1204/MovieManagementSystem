package com.mms.mms_api.business.handler.room;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.business.specification.RoomSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomSearchHandler extends RoomBaseHandler<RoomSearchQuery, PaginatedResult<RoomDto>> {
    
    public RoomSearchHandler(RoomSearchQuery request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    
    @Override
    public PaginatedResult<RoomDto> execute() {
        Sort.Direction direction = Sort.Direction.fromString(request.getSortDirection().name());

        String sortBy = request.getSortBy();

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), sort);

        Specification<Room> spec = new RoomSpecification(request);

        Page<Room> rooms = roomRepository.findAll(spec, pageable);
        
        List<RoomDto> roomDtos = rooms.getContent().stream()
                .map(roomMapper::toDto)
                .toList();

        return new PaginatedResult<>(roomDtos, rooms.getTotalElements(), rooms.getTotalPages(),
                pageable.getPageSize(), pageable.getPageNumber() + 1);
    }
    
}
