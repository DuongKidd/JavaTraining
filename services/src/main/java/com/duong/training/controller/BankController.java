/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.BankDTO;
import com.duong.training.exception.FieldDuplicateException;
import com.duong.training.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/banks")
@Slf4j
public class BankController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankController.class);

    private BankService bankService;

    @Autowired
    public BankController(final BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Create new bank
     *
     * @param bankDTO
     * @return response data
     */
    @PostMapping
    public ResponseEntity<?> createBank(@Valid @RequestBody BankDTO bankDTO) {
        BankDTO responseData = null;
        try {
            LOGGER.info("Starting create new bank!");
            responseData = bankService.createBank(bankDTO);
            LOGGER.info("New bank already created! - {}", responseData);
        } catch (FieldDuplicateException ex) {
            throw ex;
        }

        if (responseData.getBankId() != null) {
            return ResponseEntity.ok(responseData);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Page<BankDTO>> getAllBanks(
            @RequestParam(defaultValue = "", required = false) String searchValue,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "8", required = false) Integer size,
            @RequestParam(defaultValue = "bankId", required = false) String sortBy) {
        Page<BankDTO> banks = bankService.getAllBanks(searchValue, page, size, sortBy);
        return new ResponseEntity<>(banks, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BankDTO> updateBank(@RequestBody BankDTO bankDTO, @RequestParam(value = "bankId") Integer bankId) {
        BankDTO bankDTOUpdate = bankService.updateBank(bankDTO, bankId);

        return bankDTOUpdate != null ? ResponseEntity.ok(bankDTO)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBank(@PathVariable int id) {
        try {
            bankService.deleteBank(id);
            return ResponseEntity.ok("Delete bank successfully!");
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST.value());
        }

    }

    /**
     * created by Duong
     * find bank by id for update bank
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<BankDTO> findBankById(@PathVariable("id") Integer id) {
        BankDTO bankDTO = bankService.findById(id);
        return new ResponseEntity<>(bankDTO,HttpStatus.OK);
    }


    @PostMapping("validate")
    public ResponseEntity<Boolean> isDuplicateBank(@RequestBody BankDTO bankDTO) {
        Boolean result = bankService.isBankExist(bankDTO);
        return ResponseEntity.ok(result);
    }

}
