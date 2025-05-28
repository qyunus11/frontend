package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.model.User;
import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.diyabet.demo.service.ApiClient.BASE_URL;

public class HastaEkleController {
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private TextField tcField;
    @FXML private ComboBox<String> genderBox;
    @FXML private DatePicker birthDatePicker;
    @FXML private Label statusLabel;

    @FXML
    private void handleKaydet() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String tcNo = tcField.getText();
        String gender = genderBox.getValue();
        String birthDate = birthDatePicker.getValue() != null ? birthDatePicker.getValue().toString() : null;

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || tcNo.isEmpty() || gender == null || birthDate == null) {
            statusLabel.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        statusLabel.setText("Kayıt yapılıyor...");
        new Thread(() -> {
            try {
                ApiClient.hastaEkle(
                        name, surname, email, tcNo, gender, birthDate,
                        Session.getInstance().getUserId()  // doktor id'si
                        // role burada gönderilmiyor, ApiClient fonksiyonu "doctor" olarak hardcoded
                );
                Platform.runLater(() -> {
                    AlertUtils.showInfo("Başarılı", "Hasta başarıyla kaydedildi!");
                    Stage stage = (Stage) statusLabel.getScene().getWindow();
                    stage.close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("Kayıt hatası: " + e.getMessage());
                });
            }
        }).start();
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
}