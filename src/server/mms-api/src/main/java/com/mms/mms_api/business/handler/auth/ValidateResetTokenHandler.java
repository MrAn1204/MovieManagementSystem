package com.mms.mms_api.business.handler.auth;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.auth.ValidateResetTokenCommand;
import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.PasswordResetTokenRepository;
import com.mms.mms_api.model.PasswordResetToken;

@Component
public class ValidateResetTokenHandler extends BaseHandler<ValidateResetTokenCommand, Boolean> {
    private final PasswordResetTokenRepository tokenRepository;

    public ValidateResetTokenHandler(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Boolean execute(ValidateResetTokenCommand request) {
        Optional<PasswordResetToken> token = tokenRepository.findByToken(request.getToken());

        if (token.isEmpty()) {
            return false;
        } else {
            PasswordResetToken resetToken = token.get();
            return !resetToken.isUsed() && !resetToken.isExpired();
        }

    }
}
