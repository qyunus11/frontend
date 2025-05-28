package com.diyabet.demo.model;

public class Recommendation {
    private int recommendationId;
    private int patientId;
    private int doctorId;
    private String date;
    private String recommendationType; // diyet, egzersiz, insulin, vs.
    private String content;

    public int getRecommendationId() { return recommendationId; }
    public void setRecommendationId(int recommendationId) { this.recommendationId = recommendationId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getRecommendationType() { return recommendationType; }
    public void setRecommendationType(String recommendationType) { this.recommendationType = recommendationType; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}