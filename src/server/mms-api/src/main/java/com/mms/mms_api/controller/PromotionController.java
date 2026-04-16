package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.business.service.PromotionService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.promotion.PromotionDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Provides CRUD and search endpoints for promotions.
 */
@RestController
@RequestMapping("api/promotions")
@AllArgsConstructor
public class PromotionController {
    private final PromotionService promotionService;

    /**
     * Creates a promotion.
     *
     * @param request promotion create payload
     * @return created promotion
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PromotionDto> create(@Valid @ModelAttribute PromotionCreateCommand request) {
        PromotionDto promotion = promotionService.handle(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

    /**
     * Returns all promotions.
     *
     * @return list of promotions
     */
    @GetMapping
    public ResponseEntity<List<PromotionDto>> getAll() {
        List<PromotionDto> promotions = promotionService.handle(new PromotionGetAllQuery());

        return ResponseEntity.ok(promotions);
    }

    /**
     * Returns a promotion by id.
     *
     * @param id promotion identifier
     * @return promotion details
     */
    @GetMapping("/{id}")
    public ResponseEntity<PromotionDto> getById(@PathVariable UUID id) {
        PromotionDto promotion = promotionService.handle(new PromotionGetByIdQuery(id));

        return ResponseEntity.ok(promotion);
    }

    /**
     * Updates a promotion by id.
     *
     * @param id promotion identifier
     * @param request update payload
     * @return updated promotion
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PromotionDto> update(@PathVariable UUID id, @Valid @ModelAttribute PromotionUpdateCommand request) {
        request.setId(id);

        PromotionDto promotion = promotionService.handle(request);

        return ResponseEntity.ok(promotion);
    }

    /**
     * Deletes a promotion by id.
     *
     * @param id promotion identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        promotionService.handle(new PromotionDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches promotions with filter and pagination.
     *
     * @param request search criteria
     * @return paginated result of promotions
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<PromotionDto>> search(@Valid @RequestBody PromotionSearchQuery request) {
        PaginatedResult<PromotionDto> result = promotionService.handle(request);

        return ResponseEntity.ok(result);
    }
}
