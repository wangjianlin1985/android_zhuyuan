package com.mobileserver.domain;

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
    private int patientObj;
    public int getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(int patientObj) {
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
    private java.sql.Timestamp inDate;
    public java.sql.Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(java.sql.Timestamp inDate) {
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
    private String doctorObj;
    public String getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(String doctorObj) {
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