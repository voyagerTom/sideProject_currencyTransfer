package com.example.currencyPrice.controller;


import com.example.currencyPrice.dao.CoinMapDao;
import com.example.currencyPrice.entity.CoinMap;
import com.example.currencyPrice.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/coindesk")
public class CoinDeskDBController {

    @Autowired
    private CoinMapDao coinMapDao;

    @Autowired
    private CoinDeskService coinDeskService;


    /**【/coindesk/showallcoin】  查詢中英對照
     * @return
     */
    @GetMapping("/showallcoin")
    public ResponseEntity<List<CoinMap>> showAllCoin() {
        //其他過濾項目略(如禁止重複資料插入)
        List<CoinMap> coinMapList = (List<CoinMap>) coinMapDao.findAll();
        return ResponseEntity.status(200).body(coinMapList);
    }


    /**
     * 【/coindesk/getcoinbyid】  查詢一筆幣別中英對照
     *
     * @param id
     * @return
     */
    @GetMapping("/getcoinbyid")
    public ResponseEntity<CoinMap> getCoinByID(@RequestParam int id) {
        //其他過濾項目略(如禁止重複資料插入)
        Optional<CoinMap> coinMapList = coinMapDao.findById(id);
        CoinMap targetRS=coinMapList.orElse(null);

        if(targetRS==null){
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.status(200).body(targetRS);
    }


    /**【/coindesk/insert】 插入幣別中英對照
     *
     * @param coinMap
     * @return
     * @throws IOException
     */
    @PostMapping("/insert")
    public ResponseEntity<String> coinMapinsert(@RequestBody CoinMap coinMap) throws IOException {
        //其他過濾項目略(如禁止重複資料插入)

        coinMapDao.save(coinMap);
        return ResponseEntity.status(200).body(coinMap.toString() + " 資料插入成功");
    }


    //【/coindesk/deletebyabr】 刪除一筆幣別中英對照
    @DeleteMapping("/deletebyid/{coinid}")
    public ResponseEntity<String> deleteByID(@PathVariable int coinid) throws IOException {
        System.out.println(coinid);
        Optional<CoinMap> coinList = coinMapDao.findById(coinid);

        CoinMap target = coinList.orElse(null);

        if (target == null) {
            return ResponseEntity.status(200).body("資料庫無 ID:" + coinid + " 的資料可刪除");
        }
        coinMapDao.deleteById(target.getId());

        return ResponseEntity.status(200).body(target.toString() + "資料刪除成功");
    }


    //【/coindesk/updatebyabr】 更新一筆幣別中英對照
// id與變更項目 放在url  更新主體放在requestBody
    @PutMapping("/updatebyid/{coinID}/{updateItem}")
    public ResponseEntity<String> updateByID(@PathVariable int coinID, @PathVariable String updateItem, @RequestBody CoinMap coinMap) throws IOException {
        if ((!updateItem.equalsIgnoreCase("chinese")) && (!updateItem.equalsIgnoreCase("english"))) {
            return ResponseEntity.status(400).body("變更項目輸入錯誤");
        }

        Optional<CoinMap> coinList = coinMapDao.findById(coinID);
        CoinMap target = coinList.orElse(null);

        if (target == null) {
            return ResponseEntity.status(200).body("資料庫無 " + coinID + " 資料，請先添加");
        }


        target.setId(coinID);
        if (updateItem.equalsIgnoreCase("english")) {
            target.setCoinEnglish(coinMap.getCoinEnglish());
        } else {
            target.setCoinChinese(coinMap.getCoinChinese());
        }

        coinMapinsert(target);

        return ResponseEntity.status(200).body(target.toString() + " 資料更新成功");
    }


    //用英文名稱取得中文
    public String getchineseByEng(String code) {
        System.out.println("code :"+code);
        //其他過濾項目略(如禁止重複資料插入)
        List<CoinMap> coinMapList = coinMapDao.findByCoinEnglish(code);
        if (coinMapList.size()==0){
            return "Chinese Name Not Found";
        }
        CoinMap targetRS=coinMapList.get(0);
        return targetRS.getCoinChinese();
//        return targetRS.getCoinChinese();
    }
}
