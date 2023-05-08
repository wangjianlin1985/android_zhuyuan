package com.mobileclient.domain;

import java.io.Serializable;

public class Treat implements Serializable {
    /*治疗id*/
    private int treatId;
    public int getTreatId() {
        return treatId;
    }
    public void setTreatId(int treatId) {
        this.treatId = treatId;
    }

    /*治疗名称*/
    private String treatName;
    public String getTreatName() {
        return treatName;
    }
    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    /*病人*/
    private int patientObj;
    public int getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(int patientObj) {
        this.patientObj = patientObj;
    }

    /*诊断情况*/
    private String diagnosis;
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /*治疗记录*/
    private String treatContent;
    public String getTreatContent() {
        return treatContent;
    }
    public void setTreatContent(String treatContent) {
        this.treatContent = treatContent;
    }

    /*治疗结果*/
    private String treatResult;
    public String getTreatResult() {
        return treatResult;
    }
    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    /*主治医生*/
    private String doctorObj;
    public String getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(String doctorObj) {
        this.doctorObj = doctorObj;
    }

    /*治疗开始时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*疗程时间*/
    private String timeLong;
    public String getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

}