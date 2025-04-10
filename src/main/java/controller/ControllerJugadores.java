package controller;

import application.AppMain;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logic.Equipo;
import logic.Jugador;
import logic.SerieMundial;
import utility.Paths;

public class ControllerJugadores {

    @FXML
    private Button btnCrearJugador;

    @FXML
    private TableView<Jugador> tableView;

    @FXML
    private TableColumn<Jugador, Integer> numberColumn;

    @FXML
    private TableColumn<Jugador, String> nameColumn;

    @FXML
    private TableColumn<Jugador, String> positionColumn;

    @FXML
    private TableColumn<Jugador, String> equipoColumn;

    @FXML
    private TableColumn<Jugador, String> fotoColumn;

    @FXML
    private TextField searchBar;

    private ObservableList<Jugador> jugadoresObservable;

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numJugador"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("foto"));

        jugadoresObservable = FXCollections.observableArrayList();

        for (Equipo equipo : SerieMundial.getInstance().getEquipos()) {
            for (Jugador jugador : equipo.getJugadores()) {
                jugador.setEquipo(equipo.getNombre()); // Asignar equipo al jugador
                jugadoresObservable.add(jugador);
            }
        }


        fotoColumn.setCellFactory(new Callback<TableColumn<Jugador, String>, TableCell<Jugador, String>>() {
            @Override
            public TableCell<Jugador, String> call(TableColumn<Jugador, String> column) {
                return new TableCell<Jugador, String>() {
                    @Override
                    protected void updateItem(String ruta, boolean empty) {
                        super.updateItem(ruta, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            try {
                                Jugador jugador = getTableView().getItems().get(getIndex());

                                ImageView newImageView = new ImageView();

                                if (jugador.getImagenRoute() != null) {
                                    newImageView.setImage(jugador.getImagenRoute());
                                } else {
                                    Image defaultImage = new Image(getClass().getResource("/picture/icons/DefaultFoto.png").toExternalForm());
                                    newImageView.setImage(defaultImage);
                                }

                                newImageView.setFitWidth(50);
                                newImageView.setFitHeight(50);

                                setGraphic(newImageView);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                setAlignment(Pos.CENTER);
                            } catch (Exception e) {
                                System.out.println("Error al cargar la imagen: " + e.getMessage());
                                e.printStackTrace();
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        resetTableView();

        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(10 * tableView.getFixedCellSize() + 55);
    }

    // Método para restaurar la vista original de la tabla
    private void resetTableView() {
        tableView.setItems(jugadoresObservable);

        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(10 * tableView.getFixedCellSize() + 55);

        tableView.refresh();
    }

    @FXML
    private void search(ActionEvent event) {
        tableView.setItems(searchList(searchBar.getText()));
    }

    private ObservableList<Jugador> searchList(String text) {
        ObservableList<Jugador> filteredList = FXCollections.observableArrayList();
        for (Jugador jugador : jugadoresObservable) {
            if (String.valueOf(jugador.getNumJugador()).contains(text) ||
                    jugador.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(jugador);
            }
        }
        return filteredList;
    }

    public void openCrearJugador(ActionEvent actionEvent) {
        Stage stage = new Stage();
        AppMain.app.loadStage(new Stage(), Paths.REGJUGADOR, "Crear Jugador", false, Paths.ICONMAIN);

        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                resetTableView();
            }
        });
    }
}