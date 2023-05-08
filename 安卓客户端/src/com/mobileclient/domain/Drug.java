package com.mobileclient.domain;

import java.io.Serializable;

public class Drug implements Serializable {
    /*药品id*/
    private int drugId;
    public int getDrugId() {
        return drugId;
    }
    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    /*药品名称*/
    private String drugName;
    public String getDrugName() {
        return drugName;
    }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /*药品单位*/
    private String unit;
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /*药品单价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

}