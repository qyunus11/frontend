package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.model.Patient;
import com.diyabet.demo.model.User;
import com.diyabet.demo.service.ApiClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import com.diyabet.demo.util.AlertUtils;
import javafx.stage.Stage;

import java.util.List;

public class DoctorDashboardController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private ListView<String> patientListView;

    @FXML
    private void initialize() {
        String adSoyad = Session.getInstance().getName() + " " + Session.getInstance().getSurname();
        welcomeLabel.setText("Hoş geldiniz, Dr. " + adSoyad + "!");
        infoLabel.setText("Kullanıcı ID: " + Session.getInstance().getUserId() +
                " | Rol: " + Session.getInstance().getRole());

        // Tüm hastaları yükle
        new Thread(() -> {
            try {
                List<User> allUsers = ApiClient.getAllUsers();
                List<User> patients = allUsers.stream()
                        .filter(u -> "patient".equalsIgnoreCase(u.getRole()))
                        .toList();
                patientListView.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        int idx = patientListView.getSelectionModel().getSelectedIndex();
                        if (idx >= 0) {
                            User selectedHasta = patients.get(idx); // patients listesini initialize dışında da erişilebilir yap!
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/diyabet/demo/hasta_detay.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Hasta Detayları");
                                stage.setScene(new Scene(loader.load()));
                                HastaDetayController controller = loader.getController();
                                controller.setHasta(selectedHasta);
                                stage.showAndWait();
                            } catch (Exception e) {
                                AlertUtils.showError("Detay Hatası", e.getMessage());
                            }
                        }
                    }
                });
                Platform.runLater(() -> {
                    patientListView.setItems(
                            FXCollections.observableArrayList(
                                    patients.stream()
                                            .map(p -> p.getName() + " " + p.getSurname() + " (" + p.getEmail() + ")")
                                            .toList()
                            )
                    );
                });
            } catch (Exception e) {
                Platform.runLater(() -> AlertUtils.showError("Hasta Listesi", e.getMessage()));
            }
        }).start();
    }
    @FXML
    private void handleHastaEkle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/diyabet/demo/hasta_ekle.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hasta Ekle");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();

            // Kapatınca hasta listesini yenile
            initialize();
        } catch (Exception e) {
            AlertUtils.showError("Hasta Ekle", e.getMessage());
        }
    }

}