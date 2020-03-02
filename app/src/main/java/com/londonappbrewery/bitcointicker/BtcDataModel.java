package com.londonappbrewery.bitcointicker;

import org.json.JSONObject;

public class BtcDataModel {

    private Double mPrice;

    public static BtcDataModel fromJson(JSONObject jsonObject) {
        BtcDataModel data = new BtcDataModel();

        try {
            data.mPrice = jsonObject.getDouble("last");
        } catch (Exception e) {

        }

        return data;
    }

    public Double getmPrice() {
        return mPrice;
    }
}
