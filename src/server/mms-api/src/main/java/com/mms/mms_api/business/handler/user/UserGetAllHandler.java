package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import java.util.List;

import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

/**
 * Handles requests to retrieve all users.
 */
@Component
public class UserGetAllHandler extends UserBaseHandler<UserGetAllQuery, List<UserDto>> {
    /**
     * Creates a UserGetAllHandler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     */
    public UserGetAllHandler(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users.
     *
     * @param request query object
     * @return list of user DTOs
     */
    @Override
    public List<UserDto> execute(UserGetAllQuery request) {
        List<User> users = userRepository.findAll();

        return users.stream().map(u -> userMapper.toDto(u)).toList();
    }

}
