package com.example.currencyPrice.tool;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class DataProcessTool {
    public String getJsonStringFromURL(String urlStr) throws IOException {
        String urlString = urlStr;
        URL url = new URL(urlString);

        //確認是否可以上網
//        if (!isInternetAvailable()){
//            System.out.println("Bro! you have suck connection");
//            throw new RuntimeException("internet is interrupted : 網路無法連接");
//        }

        // 發起請求
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        //連線失敗直接報錯
        try {
            if (httpURLConnection.getResponseCode() != 200) {
                System.out.println("Taipei Government's url connectted but wrong");
                return null;
            }
        } catch (Exception ex) {
            System.out.println("Taipei Government's url connectted fail");
            return null;
        }


        // API讀取成功在回應
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder jsonStr = new StringBuilder();
        String lineOutput;

        // 讀取回應資料
        while ((lineOutput = in.readLine()) != null) {
            jsonStr.append(lineOutput);
        }
        in.close();
        System.out.println(jsonStr);
        return jsonStr.toString();
    }


}
