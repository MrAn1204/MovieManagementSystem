package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import java.util.Objects;

import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;

@Component
public class UserDeleteHandler extends UserBaseHandler<UserDeleteCommand, Void> {
    public UserDeleteHandler(UserRepository userRepository) {
        super(null, userRepository); // UserMapper is not needed for delete
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(UserDeleteCommand request) {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        userRepository.delete(Objects.requireNonNull(user));

        return null;
    }
}