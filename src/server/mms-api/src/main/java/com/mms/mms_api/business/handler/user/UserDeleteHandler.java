package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class UserDeleteHandler extends UserBaseHandler<UserDeleteCommand, Void> {
    public UserDeleteHandler(UserDeleteCommand request, UserRepository userRepository) {
        super(request, null, userRepository); // UserMapper is not needed for delete
        this.userRepository = userRepository;
    }

    @Override
    public Void execute() {
        if (!userRepository.existsById(request.getId())) {
            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND.getValue());
        }

        userRepository.deleteById(request.getId());
        return null;
    }
}