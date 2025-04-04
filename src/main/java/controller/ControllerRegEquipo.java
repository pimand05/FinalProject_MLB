package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import logic.Equipo;
import logic.SerieMundial;
import utility.GuardarImagen;
import utility.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerRegEquipo implements Initializable {

    @FXML private Button btnRegistrar;
    @FXML private ColorPicker cpPrimario;
    @FXML private ColorPicker cpSecundario;
    @FXML private Label lblLogo;
    @FXML private StackPane stackLogo;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtEstadio;
    @FXML private TextArea txtHistoria;
    @FXML private TextField txtNombre;

    private String stackPane;
    private File imagenTemporal = null;

    // Se invocan al arrastrar la imagen sobre el área designada.
    @FXML
    void handleDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    // Se invoca al soltar la imagen en el área
    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            imagenTemporal = db.getFiles().getFirst(); // Obtenemos el primer archivo (se puede extender para múltiples archivos)
            mostrarImagen(imagenTemporal);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    // Método para registrar el equipo
    @FXML
    void registrarEquipo(ActionEvent event) {
        if (txtNombre.getText().isEmpty() || txtEstadio.getText().isEmpty() || txtCiudad.getText().isEmpty() || cpPrimario.getValue() == null || cpSecundario.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            // alert.setHeaderText("Campos incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, completa todos los campos.");
            alert.showAndWait();
        } else {
            String nombre = txtNombre.getText();
            String ruta = Paths.EQUIPODF;

            if (imagenTemporal != null)
                ruta = GuardarImagen.guardar(imagenTemporal, "logo_"+nombre.trim().replaceAll("\\s+", "_"),Paths.FOLDEREQUIPO+nombre.trim().replaceAll("\\s+", "_"));
            Equipo equipo = new Equipo(
                    txtNombre.getText(),
                    txtEstadio.getText(),
                    txtCiudad.getText(),
                    txtHistoria.getText(),
                    cpPrimario.getValue().toString(),
                    cpSecundario.getValue().toString(),
                    ruta
            );
            SerieMundial.getInstance().addEquipo(equipo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("El equipo ha sido registrado exitosamente.");
            alert.showAndWait();
            clean();
        }
    }

    // Método para actualizar el ImageView con la imagen dropeada
    private void mostrarImagen(File file) {
        Image image = new Image(file.toURI().toString());
        showPane(image);
    }

    private void showPane(Image image) {
        String imageUrl = image.getUrl();
        stackLogo.setStyle(
                "-fx-background-image: url('" + imageUrl + "');" +
                        "-fx-background-size: contain;" +
                        "-fx-background-position: center center;" +
                        "-fx-background-color: white;"  +
                        "-fx-background-repeat: no-repeat;"
        );
        lblLogo.setVisible(false);
    }

    private void restorePane() {
        stackLogo.setStyle(stackPane);
        lblLogo.setVisible(true);
    }

    private void clean() {
        txtNombre.clear();
        txtEstadio.clear();
        txtCiudad.clear();
        txtHistoria.clear();
        cpPrimario.setValue(null);
        cpSecundario.setValue(null);
        imagenTemporal = null;
        restorePane();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stackPane = stackLogo.getStyle();
    }
}
