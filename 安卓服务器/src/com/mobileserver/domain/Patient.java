package com.mobileserver.domain;

public class Patient {
    /*����id*/
    private int patiendId;
    public int getPatiendId() {
        return patiendId;
    }
    public void setPatiendId(int patiendId) {
        this.patiendId = patiendId;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*��������*/
    private java.sql.Timestamp birthday;
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /*���֤��*/
    private String cardNo;
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /*����*/
    private String originPlace;
    public String getOriginPlace() {
        return originPlace;
    }
    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*��ϵ��ַ*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*������ʷ*/
    private String caseHistory;
    public String getCaseHistory() {
        return caseHistory;
    }
    public void setCaseHistory(String caseHistory) {
        this.caseHistory = caseHistory;
    }

}