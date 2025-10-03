package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.service.PromotionService;
import com.mms.mms_api.dto.PromotionDto;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/promotions")
@AllArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("/create")
    public ResponseEntity<PromotionDto> create(@RequestBody PromotionCreateCommand request) {
        PromotionDto promotion = promotionService.handle(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

}
