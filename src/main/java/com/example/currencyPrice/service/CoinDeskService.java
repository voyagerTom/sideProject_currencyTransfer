package com.example.currencyPrice.service;


import com.example.currencyPrice.dao.CoinMapDao;
import com.example.currencyPrice.entity.CoinMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Component
public class CoinDeskService {

    @Autowired
    private CoinMapDao coinMapDao;

    public void updatebyid(int coinID,  String updateItem, CoinMap coinMap){

    }
}
