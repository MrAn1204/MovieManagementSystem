package com.mms.mms_api.business.handler;

import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.data.UserRepository;

public class UserDeleteHandler extends BaseHandler<UserDeleteCommand, Boolean> {

    private final UserRepository userRepository;

    public UserDeleteHandler(UserDeleteCommand request, UserRepository userRepository) {
        super(request, null); // UserMapper is not needed for delete
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