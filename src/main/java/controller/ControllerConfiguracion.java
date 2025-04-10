package controller;

import application.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ControllerConfiguracion {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void initialize() {
        comboBox.getItems().addAll("INACTIVO", "ACTIVO");
        comboBox.setValue(AppMain.video ? "ACTIVO" : "INACTIVO");

        comboBox.setOnAction(this::updateVideoConfig);

    }

    private void updateVideoConfig(ActionEvent event) {
        // Update the AppMain.video variable based on ComboBox selection
        if (comboBox.getValue().equals("ACTIVO")) {
            AppMain.video = true;
        } else {
            AppMain.video = false;
        }
    }
}
