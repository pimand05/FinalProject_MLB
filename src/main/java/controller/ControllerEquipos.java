package controller;

import application.AppMain;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logic.Equipo;
import logic.SerieMundial;
import utility.Paths;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;


public class ControllerEquipos implements Initializable {

    @FXML public Button btnCrearEquipo;
    @FXML private TextField searchBar;
    @FXML private TableView<Equipo> tableView;
    @FXML private TableColumn<Equipo, String> nameColumn;
    @FXML private TableColumn<Equipo, String> imageColumn;
    @FXML private TableColumn<Equipo, String> ciudadColumn;
    @FXML private TableColumn<Equipo, String> estadioColumn;
    @FXML private TableColumn<Equipo, String> numeroColumn;

    private final ObservableList<Equipo> data = FXCollections.observableArrayList();

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
        tableView.setPrefHeight(4 * tableView.getFixedCellSize() + 55);

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

        // Modificación para la columna de imágenes en ControllerEquipos
        imageColumn.setCellFactory(new Callback<TableColumn<Equipo, String>, TableCell<Equipo, String>>() {
            @Override
            public TableCell<Equipo, String> call(TableColumn<Equipo, String> column) {
                return new TableCell<Equipo, String>() {
                    @Override
                    protected void updateItem(String ruta, boolean empty) {
                        super.updateItem(ruta, empty);

                        if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                            setGraphic(null);
                        } else {
                            Equipo equipo = getTableView().getItems().get(getIndex());
                            Image logo = null;

                            // Intentar obtener el logo usando el método getLogo de Equipo
                            try {
                                logo = equipo.getLogo();
                            } catch (Exception e) {
                                System.out.println("Error al cargar la imagen con getLogo(): " + e.getMessage());
                            }

                            // Si no se pudo cargar, usar la imagen por defecto
                            if (logo == null || logo.isError()) {
                                try {
                                    logo = new Image(getClass().getResourceAsStream("/picture/icons/DefaultIcon.png"));
                                } catch (Exception e) {
                                    System.out.println("No se pudo cargar la imagen por defecto: " + e.getMessage());
                                }
                            }

                            if (logo != null && !logo.isError()) {
                                ImageView imageView = new ImageView(logo);
                                imageView.setFitWidth(50);
                                imageView.setFitHeight(50);
                                imageView.setPreserveRatio(true);

                                setGraphic(imageView);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                                setAlignment(Pos.CENTER);
                            } else {
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
        tableView.setPrefHeight(4 * tableView.getFixedCellSize() + 55);
        tableView.refresh();

    }

    private void handleRowClick(MouseEvent event) {
        final Equipo selectedTeam = tableView.getSelectionModel().getSelectedItem();

        if (selectedTeam != null) {
            // Handle right-click with context menu
            if (event.getButton() == MouseButton.SECONDARY) {
                ContextMenu contextMenu = new ContextMenu();


                // Create "Ver información" menu item
                MenuItem infoItem = new MenuItem("Ver información");
                infoItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        System.out.println("Equipo seleccionado: " + selectedTeam.getNombre());

                        try {
                            SerieMundial.getInstance().setEquipoSeleccionado(selectedTeam);
                            Stage infoStage = new Stage();
                            AppMain.app.loadStage(infoStage, Paths.INFOEQUIPO,
                                    "Información de " + selectedTeam.getNombre(),
                                    false, Paths.ICONMAIN);
                        } catch (Exception ex) {
                            System.out.println("Error al abrir la ventana de información: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });

                // Create "Modificar" menu item
                MenuItem modificar = new MenuItem("Modificar");
                modificar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        System.out.println("Modificando equipo: " + selectedTeam.getNombre());

                        try {
                            SerieMundial.getInstance().setEquipoSeleccionado(selectedTeam);
                            Stage editStage = new Stage();
                            AppMain.app.loadStage(editStage, Paths.EDITAREQUIPO,
                                    "Modificar Equipo: " + selectedTeam.getNombre(),
                                    false, Paths.ICONMAIN);

                            // Refresh the table after the edit window is closed
                            editStage.setOnHidden(event -> resetTableView());
                        } catch (Exception ex) {
                            System.out.println("Error al abrir la ventana de modificación: " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });

                // Create "Eliminar equipo" menu item
                MenuItem deleteItem = new MenuItem("Eliminar equipo");
                deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmación");
                        alert.setHeaderText("Eliminar equipo");
                        alert.setContentText("¿Está seguro que desea eliminar el equipo " + selectedTeam.getNombre() + "?");

                        ButtonType buttonTypeYes = new ButtonType("Sí");
                        ButtonType buttonTypeNo = new ButtonType("No");

                        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                        alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                            @Override
                            public void accept(ButtonType buttonType) {
                                if (buttonType == buttonTypeYes) {
                                    SerieMundial.getInstance().eliminarEquipo(selectedTeam);
                                    resetTableView();
                                }
                            }
                        });
                    }
                });

                contextMenu.getItems().addAll(infoItem, modificar, deleteItem);
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
            }
            // Handle double-click to open info window (keep existing behavior)
            else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                System.out.println("Equipo seleccionado: " + selectedTeam.getNombre());

                try {
                    SerieMundial.getInstance().setEquipoSeleccionado(selectedTeam);
                    Stage infoStage = new Stage();
                    AppMain.app.loadStage(infoStage, Paths.INFOEQUIPO,
                            "Información de " + selectedTeam.getNombre(),
                            false, Paths.ICONMAIN);
                } catch (Exception e) {
                    System.out.println("Error al abrir la ventana de información: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void eliminarEquipo(ActionEvent actionEvent) {
        Equipo selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Eliminar equipo");
            alert.setContentText("¿Está seguro que desea eliminar el equipo " + selectedTeam.getNombre() + "?");

            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    SerieMundial.getInstance().eliminarEquipo(selectedTeam);
                    resetTableView();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un equipo para eliminar.");
            alert.showAndWait();
        }
    }

    public void openCrearEquipo(ActionEvent actionEvent) {
        Stage stage = new Stage();
        AppMain.app.loadStage(stage, Paths.RGEQUIPO, "Crear Equipo", false, Paths.ICONMAIN);
        stage.setAlwaysOnTop(true);


        stage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
