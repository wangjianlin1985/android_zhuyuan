package com.mobileserver.domain;

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
    private int treatObj;
    public int getTreatObj() {
        return treatObj;
    }
    public void setTreatObj(int treatObj) {
        this.treatObj = treatObj;
    }

    /*����ҩƷ*/
    private int drugObj;
    public int getDrugObj() {
        return drugObj;
    }
    public void setDrugObj(int drugObj) {
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