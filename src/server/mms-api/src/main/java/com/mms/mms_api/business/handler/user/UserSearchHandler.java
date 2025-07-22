package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.business.specification.UserSpecification;
import com.mms.mms_api.util.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserSearchHandler extends UserBaseHandler<UserSearchQuery, PaginatedResult<UserDto>> {
    public UserSearchHandler(UserSearchQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper, userRepository);
    }

    @Override
    public PaginatedResult<UserDto> execute() {
        Sort.Direction direction = Sort.Direction.valueOf(request.getSortDirection().name());
        
        String sortBy = request.getSortBy();

        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), sort);

        Specification<User> spec = new UserSpecification(request);

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserDto> userDtos = userPage.getContent().stream()
                .map(userMapper::toDto)
                .toList();

        return new PaginatedResult<>(userDtos, userPage.getTotalElements(), userPage.getTotalPages(),
                pageable.getPageSize(), pageable.getPageNumber() + 1);
    }
}