package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Doctor {
    /*医生工号*/
    private String doctorNo;
    public String getDoctorNo() {
        return doctorNo;
    }
    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*所在科室*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
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

    /*身份证号*/
    private String cardNo;
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /*医生照片*/
    private String doctorPhoto;
    public String getDoctorPhoto() {
        return doctorPhoto;
    }
    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    /*出生日期*/
    private Timestamp birthday;
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*毕业学校*/
    private String school;
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    /*工作经验*/
    private String workYears;
    public String getWorkYears() {
        return workYears;
    }
    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    /*备注*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}