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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logic.Equipo;
import logic.Jugador;
import logic.SerieMundial;
import utility.Paths;

import java.util.function.Consumer;


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
    public static boolean adminMode = false;

    // Método para restaurar la vista original de la tabla
    private void resetTableView() {
        ObservableList<Jugador> jugadoresObservable = FXCollections.observableArrayList(SerieMundial.getInstance().getTodosLosJugadores());
        tableView.setItems(jugadoresObservable);

        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(10 * tableView.getFixedCellSize() + 55);

        tableView.refresh();
    }

    // Método para inicializar la tabla y cargar los datos
    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numJugador"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        positionColumn.setCellValueFactory(cellData -> {
            Jugador jugador = cellData.getValue();
            if (jugador instanceof logic.Bateador bateador) {
                return new javafx.beans.property.SimpleStringProperty(bateador.getPosicion());
            } else if (jugador instanceof logic.Pitcher pitcher) {
                return new javafx.beans.property.SimpleStringProperty(pitcher.getTipoDeLanzador());
            } else {
                return new javafx.beans.property.SimpleStringProperty("N/A");
            }
        });
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("ImageRoute"));

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleRowClick);

        jugadoresObservable = FXCollections.observableArrayList();

        for (Equipo equipo : SerieMundial.getInstance().getEquipos()) {
            for (Jugador jugador : equipo.getJugadores()) {
                jugador.setEquipo(equipo.getNombre()); // Asignar equipo al jugador
                jugadoresObservable.add(jugador);
            }
        }


        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("imagenRoute")); // O el campo que uses

        fotoColumn.setCellFactory(new Callback<TableColumn<Jugador, String>, TableCell<Jugador, String>>() {
            @Override
            public TableCell<Jugador, String> call(TableColumn<Jugador, String> column) {
                return new TableCell<Jugador, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String ruta, boolean empty) {
                        super.updateItem(ruta, empty);
                        if (empty || ruta == null || ruta.isEmpty()) {
                            // Si no hay imagen, no mostramos nada o mostramos una imagen por defecto.
                            setGraphic(null);
                        } else {
                            try {
                                // Obtenemos el jugador de la fila actual.
                                Jugador jugador = getTableView().getItems().get(getIndex());

                                // Intentamos obtener la imagen ya cargada en el jugador.
                                Image image = jugador.getFoto();

                                // Si no se ha cargado o hay error, se carga la imagen por defecto.
                                if (image == null || image.isError()) {
                                    image = new Image(getClass().getResource("/picture/icons/DefaultFoto.png")
                                            .toExternalForm(), 50, 50, true, true);
                                }

                                imageView.setImage(image);
                                imageView.setFitWidth(50);
                                imageView.setFitHeight(50);
                                setGraphic(imageView);
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

        if (adminMode == false) {
            btnCrearJugador.setVisible(false);
        }

        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(10 * tableView.getFixedCellSize() + 55);
    }

    private void handleRowClick(MouseEvent mouseEvent) {
        final Jugador selectedPlayer = tableView.getSelectionModel().getSelectedItem();

        if (selectedPlayer != null) {
            // Handle right-click with context menu
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                ContextMenu contextMenu = new ContextMenu();

                // Create "Ver información" menu item
                MenuItem infoItem = new MenuItem("Ver información");
                /*infoItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        System.out.println("Jugador seleccionado: " + selectedPlayer.getNombre());

                        try {
                            SerieMundial.getInstance().setJugadorSeleccionado(selectedPlayer);
                            Stage infoStage = new Stage();
                            AppMain.app.loadStage(infoStage, Paths.INFOJUGADOR,
                                    "Información de " + selectedPlayer.getNombre(),
                                    false, Paths.ICONMAIN);
                        } catch (Exception ex) {
                            System.out.println("Error al abrir la ventana de información: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });*/

                //Create "Eliminar jugador" menu item
                MenuItem deleteItem = new MenuItem("Eliminar jugador");
                deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmación");
                        alert.setHeaderText("Eliminar jugador");
                        alert.setContentText("¿Está seguro que desea eliminar a " + selectedPlayer.getNombre() + "?");

                        ButtonType buttonTypeYes = new ButtonType("Sí");
                        ButtonType buttonTypeNo = new ButtonType("No");

                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                        alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                            @Override
                            public void accept(ButtonType buttonType) {
                                if (buttonType == buttonTypeYes) {
                                    // Get the player's team
                                    Equipo equipo = SerieMundial.getInstance().buscarEquipoPorNombre(selectedPlayer.getEquipo());
                                    if (equipo != null) {
                                        equipo.getJugadores().remove(selectedPlayer);
                                        resetTableView();
                                        jugadoresObservable.remove(selectedPlayer);
                                        tableView.refresh();
                                    }
                                }
                            }
                        });
                    }
                });

                contextMenu.getItems().addAll(infoItem, deleteItem);
                contextMenu.show(tableView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }


            // Handle double-click to open info window
            /*else if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                System.out.println("Jugador seleccionado: " + selectedPlayer.getNombre());

                try {
                    SerieMundial.getInstance().setJugadorSeleccionado(selectedPlayer);
                    Stage infoStage = new Stage();
                    AppMain.app.loadStage(infoStage, Paths.INFOJUGADOR,
                            "Información de " + selectedPlayer.getNombre(),
                            false, Paths.ICONMAIN);
                } catch (Exception e) {
                    System.out.println("Error al abrir la ventana de información: " + e.getMessage());
                    e.printStackTrace();
                }
            }*/
        }
    }

    @FXML
    private void search(ActionEvent event) {
        resetTableView();
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
        AppMain.app.openNewStage(Paths.REGJUGADOR, "Registrar Jugador", false, Paths.ICONMAIN, true);
        stage.setAlwaysOnTop(true);
        resetTableView();

    }
}