package com.diyabet.demo.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiClient {
    public static final String BASE_URL = "http://localhost:8080"; // Gerekirse değiştir

    public static LoginResponse login(String email, String password) throws Exception {
        URL url = new URL(BASE_URL + "/api/auth/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        String jsonInput = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                Gson gson = new Gson();
                return gson.fromJson(response.toString(), LoginResponse.class);
            }
        } else {
            throw new Exception("Giriş başarısız: " + code);
        }
    }
    public static List<Patient> getAllPatients() throws Exception {
        URL url = new URL(BASE_URL + "/api/patients");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        int code = conn.getResponseCode();
        if (code == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                Gson gson = new Gson();
                return gson.fromJson(response.toString(), new TypeToken<List<Patient>>(){}.getType());
            }
        } else {
            throw new Exception("Hasta listesi alınamadı: " + code);
        }
    }
    public static void hastaEkle(String name, String surname, String email, String tcNo, String gender, String birthDate, int doctorId) throws Exception {
        URL url = new URL(BASE_URL + "/api/patients");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        // DOĞRU HEADER'LAR:
        conn.setRequestProperty("X-USER-ID", String.valueOf(doctorId));
        conn.setRequestProperty("X-USER-ROLE", "doctor");

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"name\":\"%s\", \"surname\":\"%s\", \"email\":\"%s\", \"tcNo\":\"%s\", \"gender\":\"%s\", \"birthDate\":\"%s\"}",
                name, surname, email, tcNo, gender, birthDate
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int code = conn.getResponseCode();
        if (code != 200 && code != 201) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            StringBuilder error = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) error.append(line.trim());
            throw new Exception("Hasta eklenemedi: " + code + " - " + error.toString());
        }
    }
    public static List<User> getAllUsers() throws Exception {
        URL url = new URL(BASE_URL + "/api/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        int code = conn.getResponseCode();
        if (code == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                Gson gson = new Gson();
                return gson.fromJson(response.toString(), new TypeToken<List<User>>(){}.getType());
            }
        } else {
            throw new Exception("Kullanıcı listesi alınamadı: " + code);
        }
    }
    public static List<Measurement> getOlcumlerForPatient(int patientId) throws Exception {
        URL url = new URL(BASE_URL + "/api/measurements/patient/" + patientId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        int code = conn.getResponseCode();
        if (code == 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) response.append(line.trim());
                Gson gson = new Gson();
                return gson.fromJson(response.toString(), new TypeToken<List<Measurement>>(){}.getType());
            }
        } else {
            throw new Exception("Ölçümler alınamadı: " + code);
        }
    }

    public static void olcumEkle(int patientId, String type, int value, String datetime) throws Exception {
        URL url = new URL(BASE_URL + "/api/measurements");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        // HEADER'LARI EKLE!
        conn.setRequestProperty("X-USER-ID", String.valueOf(patientId));
        conn.setRequestProperty("X-USER-ROLE", "patient");

        conn.setDoOutput(true);

        String jsonInput = String.format(
                "{\"patientId\":%d, \"measurementType\":\"%s\", \"value\":%d, \"measurementDatetime\":\"%s\"}",
                patientId, type, value, datetime
        );
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int code = conn.getResponseCode();
        if (code != 200 && code != 201) {
            throw new Exception("Ölçüm eklenemedi: " + code);
        }
    }
}