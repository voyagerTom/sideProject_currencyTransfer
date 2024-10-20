package com.example.currencyPrice.dao;

import com.example.currencyPrice.entity.CoinMap;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinMapDao extends CrudRepository<CoinMap,Integer> {

    // 根據 CoinEnglish 來搜尋
    @Query(value="SELECT * FROM coin  WHERE english = :coinEnglish", nativeQuery = true)
    List<CoinMap> findByCoinEnglish( String coinEnglish);
}
