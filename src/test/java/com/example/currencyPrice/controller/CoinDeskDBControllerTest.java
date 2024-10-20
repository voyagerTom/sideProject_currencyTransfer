package com.example.currencyPrice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoinDeskDBControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void showAllCoin() throws Exception {
        RequestBuilder rb= MockMvcRequestBuilders.get("/coindesk/showallcoin");
        mockMvc.perform(rb)
                .andExpect(status().isOk());

    }

    @Test
    void getCoinByID() {
    }

    @Test
    void coinMapinsert() {
    }

    @Test
    void deleteByID() {
    }

    @Test
    void updateByID() {
    }

    @Test
    void getchineseByEng() {
    }
}