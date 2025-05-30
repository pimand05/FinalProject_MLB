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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import logic.Equipo;
import logic.SerieMundial;
import utility.Paths;

import java.net.URL;
import java.util.*;
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
    @FXML private StackPane iconBotton;
    public static boolean adminMode = false;

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
        ObservableList<Equipo> equiposObservable = FXCollections.observableArrayList(SerieMundial.getInstance().getEquipos());
        tableView.setItems(equiposObservable);


        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(4 * tableView.getFixedCellSize() + 55);

        tableView.refresh();
    }

    @FXML
    void openHome(MouseEvent event) {
        AppMain.app.changeScene(AppMain.app.getStage(), Paths.MAIN, "SERIE MUNDIAL", true,0);
    }

    private void setIconImage() {
        iconBotton.setStyle("-fx-background-image: url('" + Paths.ICONEQUIPO + "');" +
                "-fx-background-size: 60% 100%; " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIconImage();


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("rutaLogo"));
        ciudadColumn.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        estadioColumn.setCellValueFactory(new PropertyValueFactory<>("estadio"));



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
        // Configuración para la columna de imágenes
        imageColumn.setCellFactory(new Callback<TableColumn<Equipo, String>, TableCell<Equipo, String>>() {
            @Override
            public TableCell<Equipo, String> call(TableColumn<Equipo, String> column) {
                return new TableCell<Equipo, String>() {
                    @Override
                    protected void updateItem(String ruta, boolean empty) {
                        super.updateItem(ruta, empty);

                        // Comprueba si la celda está vacía o no hay item
                        if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                            setGraphic(null);
                            return;
                        }

                        // Obtén el equipo de la fila actual
                        Equipo equipo = getTableView().getItems().get(getIndex());
                        Image logo = null;

                        // Intenta obtener el logo usando el método getLogo() definido en Equipo
                        try {
                            logo = equipo.getLogo();
                        } catch (Exception e) {
                            System.out.println("Error al cargar la imagen con getLogo(): " + e.getMessage());
                        }

                        // Si no se pudo cargar la imagen, usa una imagen por defecto
                        if (logo == null || logo.isError()) {
                            try {
                                logo = new Image((getClass().getResource(Paths.EQUIPODF)).toExternalForm());
                            } catch (Exception e) {
                                System.out.println("No se pudo cargar la imagen por defecto: " + e.getMessage());
                            }
                        }

                        // Si se obtuvo una imagen válida, crea un ImageView para mostrarla
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
                };
            }
        });


        resetTableView();

        if(adminMode == false){
            btnCrearEquipo.setVisible(false);
        }

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleRowClick);


        tableView.setFixedCellSize(60);
        tableView.setPrefHeight(4 * tableView.getFixedCellSize() + 55);
        tableView.refresh();

    }

    private void handleRowClick(MouseEvent event) {
        final Equipo selectedTeam = tableView.getSelectionModel().getSelectedItem();

        if (selectedTeam != null) {
            if (event.getButton() == MouseButton.SECONDARY) {
                ContextMenu contextMenu = new ContextMenu();

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

                contextMenu.getItems().addAll(infoItem, deleteItem);
                contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
            }
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

        if(adminMode == false){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("No tienes permisos para crear un equipo.");
            alert.showAndWait();
            return;
        }else {
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
