package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Treat {
    /*����id*/
    private int treatId;
    public int getTreatId() {
        return treatId;
    }
    public void setTreatId(int treatId) {
        this.treatId = treatId;
    }

    /*��������*/
    private String treatName;
    public String getTreatName() {
        return treatName;
    }
    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    /*����*/
    private Patient patientObj;
    public Patient getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(Patient patientObj) {
        this.patientObj = patientObj;
    }

    /*������*/
    private String diagnosis;
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /*���Ƽ�¼*/
    private String treatContent;
    public String getTreatContent() {
        return treatContent;
    }
    public void setTreatContent(String treatContent) {
        this.treatContent = treatContent;
    }

    /*���ƽ��*/
    private String treatResult;
    public String getTreatResult() {
        return treatResult;
    }
    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    /*����ҽ��*/
    private Doctor doctorObj;
    public Doctor getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(Doctor doctorObj) {
        this.doctorObj = doctorObj;
    }

    /*���ƿ�ʼʱ��*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*�Ƴ�ʱ��*/
    private String timeLong;
    public String getTimeLong() {
        return timeLong;
    }
    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

}