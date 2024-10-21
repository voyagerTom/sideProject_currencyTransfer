package com.example.currencyPrice.controller;

import com.example.currencyPrice.entity.CoinMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoinDeskDBControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showAllCoin() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.get("/coindesk/showallcoin");
        mockMvc.perform(rb)
                .andExpect(status().isOk());

    }

    @Test
    void getCoinByID() throws Exception {
        int id = 2;
        RequestBuilder rb = MockMvcRequestBuilders.get("/coindesk/getcoinbyid?id=" + id);
        MvcResult rs = null;
        if (id <= 4) {
            System.out.println("預計有資料可撈到，回覆200");
            rs = mockMvc.perform(rb)
                    .andExpect(status().isOk())
                    .andReturn();
        } else {
            System.out.println("預計沒資料可撈到，回覆204");
            rs = mockMvc.perform(rb)
                    .andExpect(status().isNoContent())
                    .andReturn();
        }

        System.out.println("getCoinByID()試驗結果:--------------------");
        System.out.println(rs.getResponse().getContentAsString());
    }

    @Test
    void coinMapinsert() throws Exception {
        CoinMap insertDate = new CoinMap();
        insertDate.setCoinChinese("韓元");
        insertDate.setCoinEnglish("KOR");

        RequestBuilder rb = post("/coindesk/insert");
        MvcResult rs = mockMvc.perform(
                        post("/coindesk/insert")
                                .contentType(MediaType.APPLICATION_JSON)  // 設定請求內容類型為 JSON
                                .content(objectMapper.writeValueAsString(insertDate)))  // 設定請求內容
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("coinMapinsert()試驗結果:--------------------");
        System.out.println(rs.getResponse().getContentAsString());


    }

    @Test
    void deleteByID() throws Exception {
        int id = 20;
        RequestBuilder rb = MockMvcRequestBuilders.delete("/coindesk/deletebyid/" + id);
        MvcResult rs = mockMvc.perform(rb)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("deleteByID()試驗結果:--------------------");
        System.out.println(rs.getResponse().getContentAsString());
    }

    @Test
    void updateByID() throws Exception {
        CoinMap insertDate = new CoinMap();
        insertDate.setCoinChinese("歐洲幣");
        insertDate.setCoinEnglish("EUROOOOOOO");
        int id = 40;
        String url = "/coindesk/updatebyid/" + id + "/english";

        RequestBuilder rb = put(url);
        MvcResult rs = mockMvc.perform(put(url)
                                .contentType(MediaType.APPLICATION_JSON)  // 設定請求內容類型為 JSON
                                .content(objectMapper.writeValueAsString(insertDate)))  // 設定請求內容
                .andExpect(status().isOk())
                .andReturn();

        System.out.println("updateByID()試驗結果:--------------------");
        System.out.println(rs.getResponse().getContentAsString());


    }

}