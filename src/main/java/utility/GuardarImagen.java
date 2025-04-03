package utility;

import javafx.scene.control.Alert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GuardarImagen {

    public static String guardar(File file, String teamName, String route) {
        File destination = null;
        try {
            // Crear la carpeta destino si no existe
            File folder = new File(route);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // Forzar la extensión .png
            destination = new File(folder, teamName + ".png");

            // Leer la imagen original
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al leer la imagen");
                alert.setContentText("El archivo seleccionado no es una imagen válida.");
                alert.showAndWait();
            }
            ImageIO.write(Objects.requireNonNull(bufferedImage), "png", destination);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al guardar la imagen");
            alert.setContentText("No se pudo guardar la imagen. Asegúrate de que el archivo sea una imagen válida.");
            alert.showAndWait();
        }
        return destination.getAbsolutePath();
    }
}
