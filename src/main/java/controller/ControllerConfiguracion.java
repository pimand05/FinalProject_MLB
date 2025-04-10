package controller;

import application.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
//import jdk.internal.icu.text.NormalizerBase;
import logic.SerieMundial;
import utility.Paths;

public class ControllerConfiguracion {


    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label labelAdmin;

    @FXML
    private Button botonAdmin;

    @FXML
    public void initialize() {
        comboBox.getItems().addAll("INACTIVO", "ACTIVO");
        comboBox.setValue(AppMain.video ? "ACTIVO" : "INACTIVO");

        comboBox.setOnAction(this::updateVideoConfig);

        if(ControllerEquipos.adminMode){
            labelAdmin.setText("Administrador: " + SerieMundial.getInstance().getPasswordMap().get("admin"));
            botonAdmin.setVisible(false);
        }

    }

    private void updateVideoConfig(ActionEvent event) {

        if (comboBox.getValue().equals("ACTIVO")) {
            SerieMundial.getInstance().setVideo(true);
        }
        if( comboBox.getValue().equals("INACTIVO")) {
            SerieMundial.getInstance().setVideo(false);
        }
    }

    @FXML
    private void adminLogin(ActionEvent event) {
        AppMain.app.openNewStage(Paths.LOGIN, "Login", false, Paths.ICONMAIN, true);
        ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).close();

    }

    @FXML
    private void backup(ActionEvent event) {

    }
}
