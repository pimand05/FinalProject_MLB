package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.SerieMundial;
import utility.Paths;

import java.io.IOException;
import java.util.Objects;

public class AppMain extends Application {
    public static AppMain app;
    public static boolean video = false;
    private Stage stage;

    //Funcion para iniciar la aplicación (default).
    @Override
    public void start(Stage primaryStage) {
        app = this;
        this.stage = primaryStage;
        if (video) {
            playIntroVideo(primaryStage);
        } else {
            SerieMundial.getInstance().loadJugadores();
            SerieMundial.getInstance().loadLogoEquipo();
            SerieMundial.getInstance().loadJugadorImagen();
            loadStage(primaryStage, Paths.MAIN, "SERIE MUNDIAL", true, Paths.ICONMAIN);
        }
    }

    // Método reutilizable para cargar y mostrar una vista en el Stage que se le pase.
    public void loadStage(Stage stage, String fxmlPath, String title, boolean resizable, String pathImage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setTitle(title);
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
            stage.getIcons().add(icon);
            stage.setResizable(resizable);
            stage.centerOnScreen();
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
    public void openNewStage(String fxmlPath, String title, boolean resizable, String pathImage, boolean wait) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle(title);
            if (wait) {
                newStage.initModality(Modality.APPLICATION_MODAL);
            }
            try {
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(pathImage)));
                newStage.getIcons().add(icon);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Icon no encontrado: " + pathImage);
                alert.showAndWait();
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.ICONMAIN)));
                newStage.getIcons().add(icon);
            }
            newStage.setResizable(resizable);
            newStage.centerOnScreen();
            newStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void stop() throws Exception {
        SerieMundial.getInstance().guardar();
        super.stop();
    }

    public  void changeScene(Stage stage, String fxmlPath, String title, boolean resizable, int restaHight) { /// aqui se cambia la escena
        try {
            double width = stage.getWidth();
            double height = stage.getHeight()- restaHight;
            FXMLLoader loader = new FXMLLoader(AppMain.class.getResource(fxmlPath));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane,width,height);
            stage.setScene(scene);
            stage.setTitle(title);
            Image icon = new Image(Objects.requireNonNull(AppMain.class.getResourceAsStream(Paths.ICONMAIN)));
            stage.setResizable(resizable);
            stage.getIcons().add(icon);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void playIntroVideo(Stage stage) {
        String videoPath = Objects.requireNonNull(getClass().getResource(Paths.VIDEOINTRO)).toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        // Configurar el contenedor para el video
        StackPane videoPane = new StackPane(mediaView);
        Scene videoScene = new Scene(videoPane, 1200, 800);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.ICONMAIN)));
        stage.getIcons().add(icon);

        stage.setScene(videoScene);
        stage.centerOnScreen();
        stage.setFullScreen(true);
        stage.show();

        // Cuando el video termine, se carga la escena principal.
        mediaPlayer.setOnEndOfMedia(() -> {
            stage.setFullScreen(false);
            SerieMundial.getInstance().loadJugadores();
            SerieMundial.getInstance().loadLogoEquipo();
            SerieMundial.getInstance().loadJugadorImagen();
            loadStage(stage, Paths.MAIN, "SERIE MUNDIAL", true, Paths.ICONMAIN);
        });
        mediaPlayer.play();
    }

    public void loadHomeStage() {
        app.loadStage(stage, Paths.MAIN, "SERIE MUNDIAL", true, Paths.ICONMAIN);
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getStage() {
        return stage;
    }
}
