package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Drug {
    /*ҩƷid*/
    private int drugId;
    public int getDrugId() {
        return drugId;
    }
    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    /*ҩƷ����*/
    private String drugName;
    public String getDrugName() {
        return drugName;
    }
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    /*ҩƷ��λ*/
    private String unit;
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /*ҩƷ����*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

}