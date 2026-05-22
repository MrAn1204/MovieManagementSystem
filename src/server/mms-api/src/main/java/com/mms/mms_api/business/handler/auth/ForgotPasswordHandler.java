package com.mms.mms_api.business.handler.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mms.mms_api.business.command.auth.ForgotPasswordCommand;
import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.service.EmailService;
import com.mms.mms_api.data.PasswordResetTokenRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.model.PasswordResetToken;
import com.mms.mms_api.model.User;

@Component
public class ForgotPasswordHandler extends BaseHandler<ForgotPasswordCommand, Void> {
    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    private final UserRepository userRepository;

    private final PasswordResetTokenRepository tokenRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public ForgotPasswordHandler(EmailService emailService, TemplateEngine templateEngine,
            UserRepository userRepository, PasswordResetTokenRepository tokenRepository) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Void execute(ForgotPasswordCommand request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return null;
        }

        PasswordResetToken resetToken = new PasswordResetToken(user.getId());

        String url = frontendUrl + "/reset-password?token=" + resetToken.getToken();

        Context templateContext = new Context();
        templateContext.setVariable("name", user.getUsername());
        templateContext.setVariable("resetUrl", url);
        String template = templateEngine.process("reset-password-email", templateContext);

        emailService.send(request.getEmail(), "[MMS] Password Reset Request", template);

        tokenRepository.save(resetToken);

        return null;
    }
}
