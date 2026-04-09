package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.business.specification.UserSpecification;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Component
public class UserSearchHandler extends UserBaseHandler<UserSearchQuery, PaginatedResult<UserDto>> {
    public UserSearchHandler(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
    }

    @Override
    public PaginatedResult<UserDto> execute(UserSearchQuery request) {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<User> spec = new UserSpecification(request);

        Page<User> userPage = userRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(userPage, userMapper::toDto);
    }
}