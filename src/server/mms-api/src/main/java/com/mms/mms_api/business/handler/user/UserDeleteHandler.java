package com.mms.mms_api.business.handler.user;

import java.util.Objects;

import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;

public class UserDeleteHandler extends UserBaseHandler<UserDeleteCommand, Void> {
    public UserDeleteHandler(UserDeleteCommand request, UserRepository userRepository) {
        super(request, null, userRepository); // UserMapper is not needed for delete
        this.userRepository = userRepository;
    }

    @Override
    public Void execute() {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        userRepository.delete(Objects.requireNonNull(user));

        return null;
    }
}