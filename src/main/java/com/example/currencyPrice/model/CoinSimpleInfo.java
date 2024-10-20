package com.example.currencyPrice.model;

import com.example.currencyPrice.POJO.BPI;

import java.util.Map;

public class CoinSimpleInfo {
    private String time;
    private Map<String, BPI> BPIList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Map<String, BPI> getBPIList() {
        return BPIList;
    }

    public void setBPIList(Map<String, BPI> BPIList) {
        this.BPIList = BPIList;
    }
}
