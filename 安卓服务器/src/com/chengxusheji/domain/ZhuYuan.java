package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ZhuYuan {
    /*住院id*/
    private int zhuyuanId;
    public int getZhuyuanId() {
        return zhuyuanId;
    }
    public void setZhuyuanId(int zhuyuanId) {
        this.zhuyuanId = zhuyuanId;
    }

    /*病人*/
    private Patient patientObj;
    public Patient getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }

    /*年龄*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*住院日期*/
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
        this.inDate = inDate;
    }

    /*入住天数*/
    private int inDays;
    public int getInDays() {
        return inDays;
    }
    public void setInDays(int inDays) {
        this.inDays = inDays;
    }

    /*床位号*/
    private String bedNum;
    public String getBedNum() {
        return bedNum;
    }
    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    /*负责医生*/
    private Doctor doctorObj;
    public Doctor getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }

    /*备注信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}