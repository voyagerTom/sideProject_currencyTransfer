package com.example.currencyPrice.dao;

import com.example.currencyPrice.entity.CoinMap;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CoinMapDao extends CrudRepository<CoinMap,Integer> {

    // 根據 CoinEnglish 來搜尋
    List<CoinMap> findByCoinEnglish(String english);
}
