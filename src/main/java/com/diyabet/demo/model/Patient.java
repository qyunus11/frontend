package com.diyabet.demo.model;

public class Patient {
    private int patientId;
    private String name;
    private String surname;
    private String email;
    private String registrationDate;

    // Getter ve Setter'lar:
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    public String getRole() {
        return "Patient";
    }
}