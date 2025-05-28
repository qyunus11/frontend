package com.diyabet.demo.controller;

import com.diyabet.demo.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HastaDetayController {
    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label tcLabel;
    @FXML private Label genderLabel;
    @FXML private Label birthLabel;
    @FXML private Label regLabel;

    private User hasta;

    public void setHasta(User hasta) {
        this.hasta = hasta;
        nameLabel.setText("Ad Soyad: " + hasta.getName() + " " + hasta.getSurname());
        emailLabel.setText("E-posta: " + hasta.getEmail());
        tcLabel.setText("TC No: " + (hasta.getTcNo() != null ? hasta.getTcNo() : "-"));
        genderLabel.setText("Cinsiyet: " + (hasta.getGender() != null ? hasta.getGender() : "-"));
        birthLabel.setText("Doğum Tarihi: " + (hasta.getBirthDate() != null ? hasta.getBirthDate() : "-"));
        regLabel.setText("Kayıt Tarihi: " + (hasta.getRegistrationDate() != null ? hasta.getRegistrationDate() : "-"));
    }

    @FXML
    private void handleKapat() {
        ((Stage) nameLabel.getScene().getWindow()).close();
    }
}