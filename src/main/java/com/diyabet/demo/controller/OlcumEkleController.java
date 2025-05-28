package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OlcumEkleController {
    @FXML private ComboBox<String> typeBox;
    @FXML private TextField valueField;
    @FXML private DatePicker datePicker;
    @FXML private Label statusLabel;

    @FXML
    private void handleKaydet() {
        String type = typeBox.getValue();
        String valueStr = valueField.getText();
        String date = datePicker.getValue() != null ? datePicker.getValue().atStartOfDay().toString() : null;

        if (type == null || valueStr.isEmpty() || date == null) {
            statusLabel.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        int value;
        try {
            value = Integer.parseInt(valueStr);
        } catch (Exception e) {
            statusLabel.setText("Değer sayısal olmalı.");
            return;
        }

        statusLabel.setText("Kaydediliyor...");
        new Thread(() -> {
            try {
                ApiClient.olcumEkle(Session.getInstance().getUserId(), type, value, date);
                Platform.runLater(() -> {
                    AlertUtils.showInfo("Başarılı", "Ölçüm başarıyla kaydedildi!");
                    ((Stage) statusLabel.getScene().getWindow()).close();
                });
            } catch (Exception e) {
                Platform.runLater(() -> statusLabel.setText("Kayıt hatası: " + e.getMessage()));
            }
        }).start();
    }
}