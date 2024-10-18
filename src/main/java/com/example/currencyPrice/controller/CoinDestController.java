package com.example.currencyPrice.controller;


import com.example.currencyPrice.dao.CoinMapDao;
import com.example.currencyPrice.entity.CoinMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/coindesk")
public class CoinDestController {

    @Autowired
    private CoinMapDao coinMapDao;


    //    【/coindesk/insert】 插入幣別中英對照
    @PostMapping("/insert")
    public ResponseEntity<String> coinMapinsert(@RequestBody CoinMap coinMap) throws IOException {
        //其他過濾項目略(如禁止重複資料插入)

        coinMapDao.save(coinMap);
        return ResponseEntity.status(200).body("資料插入成功");
    }


    //【/coindesk/deletebyabr】 刪除一筆幣別中英對照
    @DeleteMapping("/deletebyabr/{coinEnglish}")
    public ResponseEntity<String> deletebyabr(@PathVariable String coinEnglish) throws IOException {
        System.out.println(coinEnglish);
        List<CoinMap> coinList   =coinMapDao.findByCoinEnglish(coinEnglish);
        if (coinList.size()==0){
            return ResponseEntity.status(200).body("資料庫無 "+coinEnglish+" 資料");
        }
        int targetID=coinList.get(0).getId();
        coinMapDao.deleteById(targetID);

        return ResponseEntity.status(200).body("資料刪除成功");
    }


//累了下面兩個明天寫lol
//【/coindesk/updatebyabr】 更新一筆幣別中英對照
//【/coindesk/getcurrencyinfobyabr】  查詢一筆幣別中英對照
//


}
