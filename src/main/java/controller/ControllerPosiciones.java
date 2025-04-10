package controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import logic.Bateador;
import logic.Equipo;
import logic.Jugador;
import logic.Pitcher;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerPosiciones implements Initializable {

    private String[] posiciones = {"1B", "2B", "3B", "C", "CF", "LF", "P", "RF", "SS"};
    private ArrayList<Pane> paneles = new ArrayList<>();

    @FXML private ImageView Image1B;
    @FXML private ImageView Image2B;
    @FXML private ImageView Image3B;
    @FXML private ImageView ImageC;
    @FXML private ImageView ImageCF;
    @FXML private ImageView ImageLF;
    @FXML private ImageView ImageP;
    @FXML private ImageView ImageRF;
    @FXML private ImageView ImageSS;

    @FXML private Label lblAVG1B;
    @FXML private Label lblAVG2B;
    @FXML private Label lblAVG3B;
    @FXML private Label lblAVGC;
    @FXML private Label lblAVGCF;
    @FXML private Label lblAVGLF;
    @FXML private Label lblAVGP;
    @FXML private Label lblAVGRF;
    @FXML private Label lblAVGSS;

    @FXML private Label lblHR1B;
    @FXML private Label lblHR2B;
    @FXML private Label lblHR3B;
    @FXML private Label lblHRC;
    @FXML private Label lblHRCF;
    @FXML private Label lblHRLF;
    @FXML private Label lblHRP;
    @FXML private Label lblHRRF;
    @FXML private Label lblHRSS;

    @FXML private Label lblHit1B;
    @FXML private Label lblHit2B;
    @FXML private Label lblHit3B;
    @FXML private Label lblHitC;
    @FXML private Label lblHitCF;
    @FXML private Label lblHitLF;
    @FXML private Label lblHitP;
    @FXML private Label lblHitRF;
    @FXML private Label lblHitSS;

    @FXML private Label lblNombre1B;
    @FXML private Label lblNombre2B;
    @FXML private Label lblNombre3B;
    @FXML private Label lblNombreC;
    @FXML private Label lblNombreCF;
    @FXML private Label lblNombreLF;
    @FXML private Label lblNombreP;
    @FXML private Label lblNombreRF;
    @FXML private Label lblNombreSS;

    @FXML private Label lblNumero1B;
    @FXML private Label lblNumero2B;
    @FXML private Label lblNumero3B;
    @FXML private Label lblNumeroC;
    @FXML private Label lblNumeroCF;
    @FXML private Label lblNumeroLF;
    @FXML private Label lblNumeroP;
    @FXML private Label lblNumeroRF;
    @FXML private Label lblNumeroSS;

    @FXML private Pane panel1B;
    @FXML private Pane panel2B;
    @FXML private Pane panel3B;
    @FXML private Pane panelC;
    @FXML private Pane panelCF;
    @FXML private Pane panelLF;
    @FXML private Pane panelP;
    @FXML private Pane panelRF;
    @FXML private Pane panelSS;



    public void fadeIn(Pane panel) {
        panel.setOpacity(0.0); // Inicialmente hacemos que el panel sea invisible
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), panel); // Creamos una transición de fade.
        fade.setFromValue(0.0);  // Comienza totalmente invisible
        fade.setToValue(1.0);    // Termina totalmente visible
        fade.play();
    }

    private Image getImageRoute(Bateador bateador, Pitcher pitcher) {
        String nombre = null;
        if (bateador != null) {
            nombre = bateador.getNombre();
        } else if (pitcher != null) {
            nombre = pitcher.getNombre();
        }

        if (nombre != null) {
            try {
                return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Photo/" + nombre + ".png")));
            } catch (Exception e) {
                System.out.println("Imagen no encontrada para: " + nombre + ".png");
            }
        }
        // Imagen por defecto si no se encuentra o hay error
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Photo/PersonIcon.png")));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fadeIn(panel1B);
        fadeIn(panel2B);
        fadeIn(panel3B);
        fadeIn(panelC);
        fadeIn(panelCF);
        fadeIn(panelLF);
        fadeIn(panelP);
        fadeIn(panelRF);
        fadeIn(panelSS);

    }
}
