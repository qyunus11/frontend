package com.diyabet.demo.model;

public class Diet {
    private int dietId;
    private int patientId;
    private String date;
    private String dietType;
    private boolean applied;

    public int getDietId() { return dietId; }
    public void setDietId(int dietId) { this.dietId = dietId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDietType() { return dietType; }
    public void setDietType(String dietType) { this.dietType = dietType; }
    public boolean isApplied() { return applied; }
    public void setApplied(boolean applied) { this.applied = applied; }
}