package com.diyabet.demo.controller;

import com.diyabet.demo.app.Session;
import com.diyabet.demo.model.User;
import com.diyabet.demo.service.ApiClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class DoctorDashboardController {

    @FXML
    private void initialize() {

        patientListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                int idx = patientListView.getSelectionModel().getSelectedIndex();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/diyabet/demo/hasta_detay.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Hasta Detayları");
                        stage.setScene(new Scene(loader.load()));
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
        } catch (Exception e) {
            AlertUtils.showError("Hasta Ekle", e.getMessage());
        }
    }
}