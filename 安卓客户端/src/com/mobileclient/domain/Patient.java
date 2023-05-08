package com.mobileclient.domain;

import java.io.Serializable;

public class Patient implements Serializable {
    /*病人id*/
    private int patiendId;
    public int getPatiendId() {
        return patiendId;
    }
    public void setPatiendId(int patiendId) {
        this.patiendId = patiendId;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*身份证号*/
    private String cardNo;
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /*籍贯*/
    private String originPlace;
    public String getOriginPlace() {
        return originPlace;
    }
    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*联系地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*病历历史*/
    private String caseHistory;
    public String getCaseHistory() {
        return caseHistory;
    }
    public void setCaseHistory(String caseHistory) {
        this.caseHistory = caseHistory;
    }

}