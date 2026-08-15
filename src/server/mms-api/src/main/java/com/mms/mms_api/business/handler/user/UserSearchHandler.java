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
import org.springframework.data.jpa.domain.Specification;

/**
 * Handles user search queries.
 */
@Component
public class UserSearchHandler extends UserBaseHandler<UserSearchQuery, PaginatedResult<UserDto>> {
    /**
     * Creates a UserSearchHandler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     */
    public UserSearchHandler(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
    }

    /**
     * Executes a paginated user search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of user DTOs
     */
    @Override
    public PaginatedResult<UserDto> execute(UserSearchQuery request) {
        Specification<User> spec = new UserSpecification(request);

        Page<User> userPage = SearchHelper.getPage(request.getPageNumber(), request.getPageSize(),
                pageable -> userRepository.findAll(spec, pageable));

        return SearchHelper.getResult(userPage, userMapper::toDto);
    }
}