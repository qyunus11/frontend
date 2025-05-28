package com.diyabet.demo.model;

public class Exercise {
    private int exerciseId;
    private int patientId;
    private String date;
    private String exerciseType;
    private boolean applied;

    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getExerciseType() { return exerciseType; }
    public void setExerciseType(String exerciseType) { this.exerciseType = exerciseType; }
    public boolean isApplied() { return applied; }
    public void setApplied(boolean applied) { this.applied = applied; }
}