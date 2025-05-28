package com.diyabet.demo.model;

public class Measurement {
    private int measurementId;
    private int patientId;
    private String measurementType; // sabah, ogle, aksam, gece, ...
    private int value;
    private String measurementDatetime;

    public int getMeasurementId() { return measurementId; }
    public void setMeasurementId(int measurementId) { this.measurementId = measurementId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public String getMeasurementDatetime() { return measurementDatetime; }
    public void setMeasurementDatetime(String measurementDatetime) { this.measurementDatetime = measurementDatetime; }
}