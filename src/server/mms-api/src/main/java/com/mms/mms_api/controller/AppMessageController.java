package com.mms.mms_api.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.service.AppMessageService;

/**
 * Exposes localized application messages for the client application.
 */
@RestController
@RequestMapping("/api/app-messages")
public class AppMessageController {
    private final AppMessageService appMessageService;

    public AppMessageController(AppMessageService appMessageService) {
        this.appMessageService = appMessageService;
    }

    /**
     * Returns message entries for a specific entity namespace.
     *
     * @param entity message namespace
     * @return map of message keys and localized values
     */
    @GetMapping("/{entity}")
    public Map<String, String> getAllByEntity(@PathVariable String entity) {
        return appMessageService.getAllByEntity(entity);
    }

    /**
     * Returns all localized message entries.
     *
     * @return map of message keys and localized values
     */
    @GetMapping()
    public Map<String, String> getAll() {
        return appMessageService.getAll();
    }

}
