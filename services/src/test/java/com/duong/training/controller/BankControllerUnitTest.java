/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.controller;

import com.duong.training.dto.BankDTO;
import com.duong.training.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.duong.training.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * fres-parent
 *
 * @author thanhdat
 * @created_at 27/04/2020 - 11:37 AM
 * @since 27/04/2020
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BankControllerUnitTest {

    private String token;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTUtils jwtTokenUtil;

    @InjectMocks
    private BankController bankController;

    @Mock
    private BankService bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
        token = jwtTokenUtil.generateToken("admin");
    }

    @Test
    public void createBank_success() throws Exception {

        BankDTO requestData = new BankDTO("DEMO01", "Demo Bank 01", "00101", 1,
                "Admin", LocalDateTime.now(), "Admin", LocalDateTime.now());

        when(bankService.createBank(ArgumentMatchers.any(BankDTO.class))).thenAnswer((Answer<BankDTO>) invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                BankDTO bank = (BankDTO) arguments[0];
                bank.setBankId(1);
                return bank;
            }
            return null;
        });

        this.mockMvc.perform(post("/banks")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.bankId", is(1)))
                .andExpect(jsonPath("$.data.code", is("DEMO01")))
                .andExpect(jsonPath("$.data.legalName", is("Demo Bank 01")))
                .andExpect(jsonPath("$.data.prefixCard", is("00101")))
                .andExpect(jsonPath("$.data.status", is(1)))
                .andExpect(jsonPath("$.data.createdBy", is("Admin")))
                .andExpect(jsonPath("$.data.modifiedBy", is("Admin")))
                .andReturn();
    }

}