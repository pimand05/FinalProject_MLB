package logic;

import javafx.scene.control.Alert;
import utility.Paths;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

public class Cliente {
    public static void backUp() {
        File json = new File(Paths.INSTANCIA); // Ejemplo de JSON
        final String ip = "127.0.0.1";
        try (Socket socket = new Socket(ip, 5000);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            byte[] jsonBytes = Files.readAllBytes(json.toPath());

            // Enviar la. longitud
            dos.writeInt(jsonBytes.length);
            // Enviar el contenido
            dos.write(jsonBytes);
            dos.flush();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Backup");
            alert.setHeaderText(null);
            alert.setContentText("Backup enviado correctamente.");
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al enviar el JSON: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
