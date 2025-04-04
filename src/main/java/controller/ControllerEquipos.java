package controller;

import application.AppMain;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Equipo;
import logic.SerieMundial;
import utility.Paths;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerEquipos implements Initializable {

    @FXML public Button btnCrearEquipo;
    @FXML private TextField searchBar;
    @FXML private TableView<Equipo> tableView;
    @FXML private TableColumn<Equipo, String> nameColumn;
    @FXML private TableColumn<Equipo, String> imageColumn;
    @FXML private TableColumn<Equipo, String> ciudadColumn;
    @FXML private TableColumn<Equipo, String> estadioColumn;
    @FXML private TableColumn<Equipo, String> numeroColumn;

    @FXML
    void search(ActionEvent event) {
        String searchText = searchBar.getText().trim();
        if (searchText.isEmpty()) {
            resetTableView();
        } else {
            tableView.setItems(searchList(searchText));
        }
    }

    // Método para restaurar la vista original de la tabla
    private void resetTableView() {
        ObservableList<Equipo> equiposObservable = FXCollections.observableArrayList(
                SerieMundial.getInstance().getEquipos());
        tableView.setItems(equiposObservable);


        tableView.setFixedCellSize(60);

        tableView.prefHeightProperty().bind(
                Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(55)
        );

        tableView.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("rutaLogo"));
        ciudadColumn.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        estadioColumn.setCellValueFactory(new PropertyValueFactory<>("estadio"));
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));



        // Configuración para la columna de número
        numeroColumn.setCellFactory(new Callback<TableColumn<Equipo, String>, TableCell<Equipo, String>>() {
            @Override
            public TableCell<Equipo, String> call(TableColumn<Equipo, String> column) {
                return new TableCell<Equipo, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                        } else {
                            // Mostrar el número de fila + 1
                            setText(String.valueOf(getIndex() + 1));
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        });

        // Configuración del cell factory para que siempre muestre la imagen default
        imageColumn.setCellFactory(new Callback<TableColumn<Equipo, String>, TableCell<Equipo, String>>() {
            @Override
            public TableCell<Equipo, String> call(TableColumn<Equipo, String> column) {
                return new TableCell<Equipo, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String ruta, boolean empty) {
                        super.updateItem(ruta, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            try {
                                // Crear una nueva instancia de ImageView por cada actualización
                                ImageView newImageView = new ImageView();
                                Image defaultImage = new Image(getClass().getResource("/picture.Icons/DefaultIcon.png").toExternalForm());
                                newImageView.setImage(defaultImage);
                                newImageView.setFitWidth(50);
                                newImageView.setFitHeight(50);
                                // Centrar la imagen
                                setGraphic(newImageView);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                setAlignment(Pos.CENTER);
                            } catch (Exception e) {
                                System.out.println("Error al cargar la imagen por defecto.");
                                e.printStackTrace();
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        });

        resetTableView();

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleRowClick);


        tableView.setFixedCellSize(60);

        tableView.prefHeightProperty().bind(
                Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(55)
        );
    }

    private void handleRowClick(MouseEvent event) {
        Equipo selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null && event.getClickCount() == 2) { // Detect double-click
            System.out.println("Equipo seleccionado: " + selectedTeam.getNombre());

            try {

                SerieMundial.getInstance().setEquipoSeleccionado(selectedTeam);

                Stage infoStage = new Stage();
                AppMain.app.loadStage(infoStage, Paths.INFOEQUIPO, "Información de " + selectedTeam.getNombre(),
                        false, Paths.ICONMAIN);

            } catch (Exception e) {
                System.out.println("Error al abrir la ventana de información: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void openCrearEquipo(ActionEvent actionEvent) {
        Stage stage = new Stage();
        AppMain.app.loadStage(stage, Paths.RGEQUIPO, "Crear Equipo", false, Paths.ICONMAIN);


        stage.setOnHidden(new javafx.event.EventHandler<javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {

                resetTableView();
            }
        });
    }

    private ObservableList<Equipo> searchList(String searchWords) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        ObservableList<Equipo> result = FXCollections.observableArrayList();

        for (Equipo team : SerieMundial.getInstance().getEquipos()) {
            boolean matchesAllWords = true;

            for (String word : searchWordsArray) {
                if (!team.getNombre().toLowerCase().contains(word.toLowerCase())) {
                    matchesAllWords = false;
                    break;
                }
            }

            if (matchesAllWords) {
                result.add(team);
            }
        }
        return result;
    }
}
