module com.diyabet.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.diyabet.demo.app to javafx.graphics, javafx.fxml;
    opens com.diyabet.demo.controller to javafx.fxml;
    opens com.diyabet.demo.model to com.google.gson;
}