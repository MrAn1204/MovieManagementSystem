package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.data.UserRepository;

public class UserDeleteHandler extends UserBaseHandler<UserDeleteCommand, Boolean> {
    public UserDeleteHandler(UserDeleteCommand request, UserRepository userRepository) {
        super(request, null, userRepository); // UserMapper is not needed for delete
        this.userRepository = userRepository;
    }

    @Override
    public Boolean execute() {
        if (!userRepository.existsById(request.getId())) {
            return false;
        }
        userRepository.deleteById(request.getId());
        return true;
    }
}