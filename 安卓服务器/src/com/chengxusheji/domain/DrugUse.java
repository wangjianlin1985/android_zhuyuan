package com.chengxusheji.domain;

import java.sql.Timestamp;
public class DrugUse {
    /*��ҩid*/
    private int drugUseId;
    public int getDrugUseId() {
        return drugUseId;
    }
    public void setDrugUseId(int drugUseId) {
        this.drugUseId = drugUseId;
    }

    /*��������*/
    private Treat treatObj;
    public Treat getTreatObj() {
        return treatObj;
    }
    public void setTreatObj(Treat treatObj) {
        this.treatObj = treatObj;
    }

    /*����ҩƷ*/
    private Drug drugObj;
    public Drug getDrugObj() {
        return drugObj;
    }
    public void setDrugObj(Drug drugObj) {
        this.drugObj = drugObj;
    }

    /*��ҩ����*/
    private float drugCount;
    public float getDrugCount() {
        return drugCount;
    }
    public void setDrugCount(float drugCount) {
        this.drugCount = drugCount;
    }

    /*ҩƷ����*/
    private float drugMoney;
    public float getDrugMoney() {
        return drugMoney;
    }
    public void setDrugMoney(float drugMoney) {
        this.drugMoney = drugMoney;
    }

    /*��ҩʱ��*/
    private String useTime;
    public String getUseTime() {
        return useTime;
    }
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

}