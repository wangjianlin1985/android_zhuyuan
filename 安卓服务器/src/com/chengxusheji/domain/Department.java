package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Department {
    /*���ұ��*/
    private String departmentNo;
    public String getDepartmentNo() {
        return departmentNo;
    }
    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    /*��������*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*��������*/
    private Timestamp bornDate;
    public Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*������*/
    private String chargeMan;
    public String getChargeMan() {
        return chargeMan;
    }
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }

}