package com.mms.mms_api.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.service.AppMessageService;

@RestController
@RequestMapping("/api/app-messages")
public class AppMessageController {
    private final AppMessageService appMessageService;

    public AppMessageController(AppMessageService appMessageService) {
        this.appMessageService = appMessageService;
    }

    @GetMapping("/{entity}")
    public Map<String, String> getAllByEntity(@PathVariable String entity) {
        return appMessageService.getAllByEntity(entity);
    }

}
