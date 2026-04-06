package com.mms.mms_api.business.service;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;

@Service
public class AppMessageService {
    private final MessageSource messageSource;

    private final Set<String> messageKeys;

    public AppMessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageKeys = loadKeys();
    }

    private Set<String> loadKeys() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.ROOT);
        return resourceBundle.keySet();
    }

    public String getByCode(String code, Object... args) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        
        Locale locale = LocaleContextHolder.getLocale();
        
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("{", "}");

        try {
            String rawMessage = messageSource.getMessage(code, args, locale);

            if (args == null || args.length == 0) {
                return rawMessage;
            }

            LinkedList<Object> argQueue = new LinkedList<>(Set.of(args));

            return helper.replacePlaceholders(rawMessage, placeholder -> (String) argQueue.poll());
        } catch (NoSuchMessageException e) {
            return String.format("[%s]", code);
        }
    }

    public Map<String, String> getAllByEntity(String entity) {
        String prefix = entity + ".";

        return messageKeys.stream().filter(key -> key.startsWith(prefix))
                .collect(Collectors.toMap(key -> key, this::getByCode));
    }

    public Map<String, String> getAll() {
        return messageKeys.stream()
                .collect(Collectors.toMap(key -> key, this::getByCode));
    }
}
