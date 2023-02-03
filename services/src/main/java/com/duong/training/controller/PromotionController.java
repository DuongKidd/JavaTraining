/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.PromotionDTO;
import com.duong.training.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public ResponseEntity<Page<PromotionDTO>> findAllPromotion(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam(defaultValue = "promotionId", required = false) String sortBy) {
        Page<PromotionDTO> result = promotionService.findAllPromotion(
                searchValue, page, size, sortBy);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionDTO> findById(@PathVariable("id") Integer id) {
        PromotionDTO result = promotionService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<PromotionDTO> createPromotion(
            @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO result = promotionService.createAndUpdatePromotion(promotionDTO, promotionDTO.getPromotionId());
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionDTO> updatePromotion(
            @RequestBody PromotionDTO promotionDTO, @PathVariable("id") Integer id) {
        PromotionDTO result = promotionService.createAndUpdatePromotion(promotionDTO, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PromotionDTO> deletePromotion(
            @PathVariable("id") Integer id) {
        PromotionDTO result = promotionService.deletePromotion(id);
        return ResponseEntity.ok(result);
    }

}
