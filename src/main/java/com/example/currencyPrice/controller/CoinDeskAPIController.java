package com.example.currencyPrice.controller;

import com.example.currencyPrice.POJO.BPI;
import com.example.currencyPrice.model.CoinSimpleInfo;
import com.example.currencyPrice.tool.DataProcessTool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CoinDeskAPIController {


    @Autowired
    private DataProcessTool dataProcessTool;
    @Autowired
    private CoinDeskDBController coinDeskDBController;

    /**叫 coindesk 的 API，單純取的JSON。
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/getrowdata")
    public ResponseEntity<String> getRowdata() throws IOException {
        System.out.println("呼叫coindesk的API開始");
        String url ="https://api.coindesk.com/v1/bpi/currentprice.json";
        //取得網址的json資料
        String jsonStr = dataProcessTool.getJsonStringFromURL(url);
        if (jsonStr == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("匯率資料無法連線");

        }
        return ResponseEntity.status(200).body(jsonStr);
    }


    /**【/getcoinsimpledata get】呼叫 coindesk 的 API,並進行資料轉換,組成新 API。
     *
     */
    @GetMapping("/getcoinsimpledata")
    public ResponseEntity<CoinSimpleInfo> getCoinSimpleData() throws IOException {
        System.out.println("getcoinsimpledata 開始");
        CoinSimpleInfo rtnData = new CoinSimpleInfo(); //用來裝回傳資料

        String json=getRowdata().getBody(); //取得getRowdata() 過來的json字串

        //酷斃了，竟然還有json 可以用!!!!dame~
        //***主要用途為將json 轉成物件，唯一要注意的是，多重結構的json，一定要拆解(用jsonNode)的單一物件才能封裝成物件
        //會被拆成樹狀結構，以KV去做配對取值
        //取的json中的KV (第一層拆解)
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode  =  objectMapper.readTree(json);
        System.out.println(jsonNode);

        // 提取特定欄位
        // ****取出時間
        //再從原KV中取的k=time  (第二層拆解)
        JsonNode timejsonNode  =  jsonNode.get("time");
        String timeupdated= timejsonNode.get("updatedISO").asText();
        timeupdated=timeConvert(timeupdated);   //轉成題目要的格式
        rtnData.setTime(timeupdated);

        //***取出BPIList
        //BPIjsonNode <--- 此物件內還有其他物件  (第三層拆解)
        JsonNode BPIjsonNodeList  =  jsonNode.get("bpi");
        Map<String, BPI> BPIMap = BPIProcessor(BPIjsonNodeList);
        rtnData.setBPIList(BPIMap);
        return ResponseEntity.status(200).body(rtnData);
    }


    private Map<String, BPI>  BPIProcessor(JsonNode BPIjsonNodeList){
        Map<String, BPI> BPIMap=new HashMap<>();
        // 將 JsonNode 轉換為 Map
        Iterator<String> fieldNames = BPIjsonNodeList.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode BPIjsonNod=BPIjsonNodeList.get(fieldName);

            //設定物件BPI 屬性
            String code= BPIjsonNod.get("code").toString().replace("\"","");
            String codeChinese=coinDeskDBController.getchineseByEng(code);
            String floatRate = BPIjsonNod.get("rate_float").toString().replace("\"","");;
            BPI bpi =new BPI();
            bpi.setCode(code);
            bpi.setCode_chinese(codeChinese);
            bpi.setRate_float(Float.parseFloat(floatRate));
            BPIMap.put(fieldName, bpi);
        }
        return BPIMap;
    }


    private String timeConvert(String timeString){
        System.out.println("Oct 20, 2024 12:40:13 UTC");
        System.out.println(timeString);

        // 原始的 ISO 8601 格式日期時間
        String isoDateTime = timeString;

        // 解析成 OffsetDateTime 對象
        OffsetDateTime dateTime = OffsetDateTime.parse(isoDateTime);

        // 定義目標格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        // 轉換成指定格式的字符串
        String formattedDateTime = dateTime.format(formatter);

        // 輸出轉換後的結果
//        System.out.println(formattedDateTime);

        return formattedDateTime;
    }

}
