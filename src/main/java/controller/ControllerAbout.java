package controller;

import application.AppMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import utility.Paths;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAbout implements Initializable {

    @FXML private StackPane IconImage;
    @FXML private StackPane manuelFoto;
    @FXML private StackPane mariaFoto;
    @FXML private StackPane sebastianFoto;
    @FXML private Label labelTittle;

    private void setIconImage() {
        IconImage.setStyle("-fx-background-image: url('" + Paths.ICONABOUTWH + "');" +
                "-fx-background-size: 75% 75%; " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center;");
    }

    private void setMariaFoto() {
        mariaFoto.setStyle("-fx-background-image: url('" + Paths.MARIAFOTO + "');" +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-radius: 20; " +
                "-fx-background-position: center;" +
                "-fx-background-size: contain;"  // 👈 esta línea es clave
        );
    }
    private void setSebastianFotoFoto() {
        sebastianFoto.setStyle("-fx-background-image: url('" + Paths.SEBAFOTO + "');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: center;" +
                        "-fx-background-radius: 20;" +
                        "-fx-background-size: contain;"  // 👈 esta línea es clave
        );
    }

    private void setManuelFoto() {
        manuelFoto.setStyle( "-fx-background-image: url('" + Paths.MANUELFOTO + "');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-position: center;" +
                        "-fx-background-radius: 20;" +
                        "-fx-background-size: contain;"  // 👈 esta línea es clave
        );
    }

    @FXML
    void openHome(MouseEvent event) {
        AppMain.app.changeScene(AppMain.app.getStage(), Paths.MAIN, "SERIE MUNDIAL", true,0);
    }

    private void changeLabel() {
        labelTittle.setStyle(labelTittle.getStyle() + " -fx-text-fill: white;");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIconImage();
        setMariaFoto();
        setSebastianFotoFoto();
        setManuelFoto();
        changeLabel();
    }
}