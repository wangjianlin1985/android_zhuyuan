package com.mobileserver.domain;

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
    private int patientObj;
    public int getPatientObj() {
        return patientObj;
    }
    public void setPatientObj(int patientObj) {
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
    private String doctorObj;
    public String getDoctorObj() {
        return doctorObj;
    }
    public void setDoctorObj(String doctorObj) {
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