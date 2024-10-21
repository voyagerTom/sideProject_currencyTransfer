package com.example.currencyPrice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

class CoinDeskAPIControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRowdata() throws Exception {
        RequestBuilder rb= MockMvcRequestBuilders.get("/api/getrowdata");
        MvcResult rs =mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("getRowdata()試驗結果:--------------------" );
        System.out.println(rs.getResponse().getContentAsString());
    }

    @Test
    void getCoinSimpleData() throws Exception {
        RequestBuilder rb= MockMvcRequestBuilders.get("/api/getcoinsimpledata");

        MvcResult rs=mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("getCoinSimpleData()試驗結果:--------------------" );
        System.out.println(rs.getResponse().getContentAsString());
    }
}