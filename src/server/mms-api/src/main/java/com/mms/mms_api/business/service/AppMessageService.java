package com.mms.mms_api.business.service;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
        Locale locale = LocaleContextHolder.getLocale();
        return getByCode(code, locale, args);
    }

    private String getByCode(String code, @NonNull Locale locale, Object... args) {
        if (code == null || code.isEmpty()) {
            return null;
        }

        try {
            return messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            return String.format("[%s]", code);
        }
    }

    public Map<String, String> getAllByEntity(String entity, Object... args) {
        String prefix = entity + ".";

        Locale locale = LocaleContextHolder.getLocale();
        
        return messageKeys.stream().filter(key -> key.startsWith(prefix))
                .collect(Collectors.toMap(key -> key, key -> getByCode(key, locale, args)));
    }
}
