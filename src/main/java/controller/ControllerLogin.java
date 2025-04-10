package controller;

import application.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLogin {

    @FXML
    private TextField usuarioTextField;

    @FXML
    private TextField contraTextField;

    @FXML
    private Button botonLogin;


    @FXML
    private void setBotonLogin(ActionEvent event) {

            String usuario = usuarioTextField.getText();
            String contrasena = contraTextField.getText();

            // Aquí puedes agregar la lógica para verificar el inicio de sesión
            if (usuario.equals("admin") && contrasena.equals("1234")) {
                //La variable booleanan pasa a ser verdadera
                ControllerEquipos.adminMode = true;
                ControllerJugadores.adminMode = true;

                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Exito");
                alert.setHeaderText(null);
                alert.setContentText("Bienvenido Administrador");
                alert.showAndWait();
                ((Stage) botonLogin.getScene().getWindow()).close();
            } else {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.");
                alert.showAndWait();
            }
        }
    }


