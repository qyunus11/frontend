package com.diyabet.demo.controller;

import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DoktorDiyetEkleController {
    private int patientId;
    private int doctorId;
    public void setPatientId(int id) { this.patientId = id; }
    public void setDoctorId(int id) { this.doctorId = id; }

    @FXML private ComboBox<String> typeBox;
    @FXML private DatePicker datePicker;
    @FXML private CheckBox appliedBox;
    @FXML private Label statusLabel;

    @FXML
    private void handleKaydet() {
        String type = typeBox.getValue();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
        boolean applied = appliedBox.isSelected();
        if (type == null || date == null) {
            statusLabel.setText("Lütfen tüm alanları doldurun.");
            return;
        }
        statusLabel.setText("Kaydediliyor...");
        new Thread(() -> {
            try {
                ApiClient.doktorDiyetEkle(patientId, doctorId, date, type, applied);
                Platform.runLater(() -> {
                    AlertUtils.showInfo("Başarılı", "Doktor diyet kaydetti!");
                    ((Stage) statusLabel.getScene().getWindow()).close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Kayıt hatası: " + e.getMessage()));
            }
        }).start();
    }
}