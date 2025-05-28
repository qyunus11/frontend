package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RecommendationEkleController {
    private int patientId;
    public void setPatientId(int patientId) { this.patientId = patientId; }

    @FXML private ComboBox<String> typeBox;
    @FXML private DatePicker datePicker;
    @FXML private TextArea contentArea;
    @FXML private Label statusLabel;

    @FXML
    private void handleKaydet() {
        String type = typeBox.getValue();
        String date = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
        String content = contentArea.getText();
        if (type == null || date == null || content.isEmpty()) {
            statusLabel.setText("Lütfen tüm alanları doldurun.");
            return;
        }
        statusLabel.setText("Kaydediliyor...");
        new Thread(() -> {
            try {
                ApiClient.addRecommendation(patientId, Session.getInstance().getUserId(), date, type, content);
                Platform.runLater(() -> {
                    AlertUtils.showInfo("Başarılı", "Öneri kaydedildi!");
                    ((Stage) statusLabel.getScene().getWindow()).close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Kayıt hatası: " + e.getMessage()));
            }
        }).start();
    }
}