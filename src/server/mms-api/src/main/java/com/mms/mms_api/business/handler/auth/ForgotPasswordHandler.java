package com.mms.mms_api.business.handler.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mms.mms_api.business.command.auth.ForgotPasswordCommand;
import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.service.EmailService;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.RandomTokenHelper;

@Component
public class ForgotPasswordHandler extends BaseHandler<ForgotPasswordCommand, Void> {
    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    private final UserRepository userRepository;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public ForgotPasswordHandler(EmailService emailService, TemplateEngine templateEngine, UserRepository userRepository) {
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(ForgotPasswordCommand request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            return null;
        }

        String token = RandomTokenHelper.generateToken();

        String url = frontendUrl + "/reset-password?token=" + token;

        Context templateContext = new Context();
        templateContext.setVariable("name", user.getUsername());
        templateContext.setVariable("resetUrl", url);
        String template = templateEngine.process("reset-password-email", templateContext);

        emailService.send(request.getEmail(), "[MMS] Password Reset Request", template);
        
        return null;
    }
}
