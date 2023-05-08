package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Department {
    /*科室编号*/
    private String departmentNo;
    public String getDepartmentNo() {
        return departmentNo;
    }
    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo;
    }

    /*科室名称*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*成立日期*/
    private Timestamp bornDate;
    public Timestamp getBornDate() {
        return bornDate;
    }
    public void setBornDate(Timestamp bornDate) {
        this.bornDate = bornDate;
    }

    /*负责人*/
    private String chargeMan;
    public String getChargeMan() {
        return chargeMan;
    }
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }

}