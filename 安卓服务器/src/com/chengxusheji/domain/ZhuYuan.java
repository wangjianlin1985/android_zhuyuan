package com.chengxusheji.domain;

import java.sql.Timestamp;
public class ZhuYuan {
    /*סԺid*/
    private int zhuyuanId;
    public int getZhuyuanId() {
        return zhuyuanId;
    }
    public void setZhuyuanId(int zhuyuanId) {
        this.zhuyuanId = zhuyuanId;
    }

    /*����*/
    private Patient patientObj;
    public Patient getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }

    /*����*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*סԺ����*/
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
        this.inDate = inDate;
    }

    /*��ס����*/
    private int inDays;
    public int getInDays() {
        return inDays;
    }
    public void setInDays(int inDays) {
        this.inDays = inDays;
    }

    /*��λ��*/
    private String bedNum;
    public String getBedNum() {
        return bedNum;
    }
    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    /*����ҽ��*/
    private Doctor doctorObj;
    public Doctor getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }

    /*��ע��Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}