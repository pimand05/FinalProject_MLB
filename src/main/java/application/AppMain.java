package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utility.Paths;

import java.io.IOException;

public class AppMain extends Application {

    public static AppMain app;     //Referencia a la aplicación.

    //Funcion para iniciar la aplicación (default).
    @Override
    public void start(Stage primaryStage) {
        app = this;
        loadStage(primaryStage, Paths.MAIN);  // Carga la primera vista en la ventana principal.
    }

    // Método reutilizable para cargar y mostrar una vista en el Stage que se le pase.
    private void loadStage(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Método para abrir una nueva ventana (Stage) con una vista específica.
    public void openNewStage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
