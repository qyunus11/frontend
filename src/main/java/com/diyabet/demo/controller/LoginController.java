package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import com.diyabet.demo.model.LoginResponse;
import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        statusLabel.setText("");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Email ve şifre gereklidir.");
            return;
        }

        loginButton.setDisable(true);
        statusLabel.setText("Giriş yapılıyor...");

        new Thread(() -> {
            try {
                LoginResponse resp = ApiClient.login(email, password);

                javafx.application.Platform.runLater(() -> {
                    // Session bilgilerini doldur
                    Session.getInstance().setUserId(resp.getUserId());
                    Session.getInstance().setRole(resp.getRole());
                    Session.getInstance().setName(resp.getName());
                    Session.getInstance().setSurname(resp.getSurname());

                    // Test için: popup ile göstermek istersen
                    // AlertUtils.showInfo("Başarılı", "Rol: " + resp.getRole() + ", ID: " + resp.getUserId() +
                    //     "\nAd: " + resp.getName() + " " + resp.getSurname());

                    // Doğrudan role göre ana panele geçiş
                    loadDashboardForRole(resp.getRole());
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    statusLabel.setText("Giriş başarısız: " + e.getMessage());
                    loginButton.setDisable(false);
                });
            }
        }).start();
    }

    // Rolüne göre uygun paneli yükler
    private void loadDashboardForRole(String role) {
        try {
            String fxmlPath;
            String windowTitle;
            if ("doctor".equalsIgnoreCase(role)) {
                fxmlPath = "/com/diyabet/demo/doctor_dashboard.fxml";
                windowTitle = "Diyabet Takip Sistemi - Doktor Paneli";
            } else {
                fxmlPath = "/com/diyabet/demo/patient_dashboard.fxml";
                windowTitle = "Diyabet Takip Sistemi - Hasta Paneli";
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(windowTitle);

        } catch (Exception e) {
            AlertUtils.showError("Panel Yükleme Hatası", e.getMessage());
            e.printStackTrace();
        }
    }
}