package com.mms.mms_api.business.handler.auth;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.auth.PasswordResetCommand;
import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.PasswordResetTokenRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.PasswordResetToken;
import com.mms.mms_api.model.User;

@Component
public class PasswordResetHandler extends BaseHandler<PasswordResetCommand, Void> {
    private final PasswordResetTokenRepository tokenRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public PasswordResetHandler(PasswordResetTokenRepository tokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Void execute(PasswordResetCommand request) {
        PasswordResetToken token = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new InvalidInputException("user.password.token.invalid"));

        Optional<User> user = userRepository.findById(token.getUserId());

        if (token.isUsed() || token.isExpired() || user.isEmpty()) {
            throw new InvalidInputException("user.password.token.invalid");
        } else {
            User existingUser = user.get();

            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(existingUser);
    
            token.setUsed(true);
            tokenRepository.save(token);
        }

        return null;
    }

}
