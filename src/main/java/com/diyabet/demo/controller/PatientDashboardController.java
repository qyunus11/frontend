package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.model.Measurement;
import com.diyabet.demo.service.ApiClient;
import com.diyabet.demo.util.AlertUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PatientDashboardController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private ListView<String> olcumListView;

    @FXML
    private void initialize() {
        String adSoyad = Session.getInstance().getName() + " " + Session.getInstance().getSurname();
        welcomeLabel.setText("Hoş geldiniz, " + adSoyad + "!");
        infoLabel.setText("Kullanıcı ID: " + Session.getInstance().getUserId() +
                " | Rol: " + Session.getInstance().getRole());

        loadOlcumler();
    }

    private void loadOlcumler() {
        new Thread(() -> {
            try {
                List<Measurement> olcumler = ApiClient.getOlcumlerForPatient(Session.getInstance().getUserId());
                Platform.runLater(() -> {
                    olcumListView.setItems(
                            FXCollections.observableArrayList(
                                    olcumler.stream()
                                            .map(o -> o.getMeasurementDatetime() + " | " + o.getMeasurementType() + " | " + o.getValue())
                                            .toList()
                            )
                    );
                });
            } catch (Exception e) {
                Platform.runLater(() -> AlertUtils.showError("Ölçüm Listesi", e.getMessage()));
            }
        }).start();
    }

    @FXML
    private void handleOlcumEkle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/diyabet/demo/olcum_ekle.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Ölçüm Ekle");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
            loadOlcumler(); // Eklendikten sonra yenile
        } catch (Exception e) {
            AlertUtils.showError("Ölçüm Ekle", e.getMessage());
        }
    }

    @FXML
    private void handleDiyetEgzersiz() {
        // Sonraki adımda ekleyelim!
        AlertUtils.showInfo("Yakında", "Bu özellik ekleniyor!");
    }

    @FXML
    private void handleBildirimiGor() {
        // Sonraki adımda ekleyelim!
        AlertUtils.showInfo("Yakında", "Bu özellik ekleniyor!");
    }
}