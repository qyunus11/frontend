package com.diyabet.demo.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.diyabet.demo.util.AlertUtils;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/diyabet/demo/login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Diyabet Takip Sistemi - Giriş");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            AlertUtils.showError("Başlatma Hatası", e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}