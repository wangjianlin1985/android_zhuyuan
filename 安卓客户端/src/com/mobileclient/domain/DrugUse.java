package com.mobileclient.domain;

import java.io.Serializable;

public class DrugUse implements Serializable {
    /*用药id*/
    private int drugUseId;
    public int getDrugUseId() {
        return drugUseId;
    }
    public void setDrugUseId(int drugUseId) {
        this.drugUseId = drugUseId;
    }

    /*治疗名称*/
    private int treatObj;
    public int getTreatObj() {
        return treatObj;
    }
    public void setTreatObj(int treatObj) {
        this.treatObj = treatObj;
    }

    /*所用药品*/
    private int drugObj;
    public int getDrugObj() {
        return drugObj;
    }
    public void setDrugObj(int drugObj) {
        this.drugObj = drugObj;
    }

    /*用药数量*/
    private float drugCount;
    public float getDrugCount() {
        return drugCount;
    }
    public void setDrugCount(float drugCount) {
        this.drugCount = drugCount;
    }

    /*药品费用*/
    private float drugMoney;
    public float getDrugMoney() {
        return drugMoney;
    }
    public void setDrugMoney(float drugMoney) {
        this.drugMoney = drugMoney;
    }

    /*用药时间*/
    private String useTime;
    public String getUseTime() {
        return useTime;
    }
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

}