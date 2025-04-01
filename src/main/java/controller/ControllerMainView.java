package controller;

import application.AppMain;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utility.Paths;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainView implements Initializable {

    @FXML
    private StackPane btnMenuClose;

    @FXML
    private StackPane btnOpenMenu;

    @FXML
    private AnchorPane slideMenu;

    @FXML
    void closeMenu(MouseEvent event) {
        TranslateTransition slider = new TranslateTransition();
        slider.setDuration(Duration.seconds(0.4));
        slider.setNode(slideMenu);

        slider.setToX(-500);
        slider.play();

        slider.setOnFinished(event1 -> {
            btnOpenMenu.setVisible(true);
            btnMenuClose.setVisible(false);
        });

    }

    @FXML
    void openMenu(MouseEvent event) {
        TranslateTransition slider = new TranslateTransition();
        slider.setDuration(Duration.seconds(0.4));
        slider.setNode(slideMenu);

        slider.setToX(0);
        slider.play();

        slider.setOnFinished(event1 -> {
            btnOpenMenu.setVisible(false);
            btnMenuClose.setVisible(true);
        });

    }

    @FXML
    void openAbout(ActionEvent event) {

    }

    @FXML
    void openDashboard(ActionEvent event) {

    }

    @FXML
    void openEquipo(ActionEvent event) {
        AppMain.app.openNewStage(Paths.POSICIONES, "Posiciones", false);
    }

    @FXML
    void openJugadores(ActionEvent event) {

    }

    @FXML
    void openTorneo(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slideMenu.setTranslateX(-500);
            btnOpenMenu.setVisible(true);
            btnMenuClose.setVisible(false);

    }


}