package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import logic.Equipo;
import logic.Jugador;
import logic.SerieMundial;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInfoEquipo implements Initializable {

    @FXML
    private TextArea textAreaHistoria;

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
    private TableView<Estadistica> tableView2;

    @FXML
    private TableColumn<Estadistica, String> categoriaColumn;

    @FXML
    private TableColumn<Estadistica, String> valorColumn;

    @FXML
    private Label labelJugador;

    private ObservableList<Jugador> jugadoresObservable;
    private ObservableList<Estadistica> estadisticasObservable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Equipo equipo = SerieMundial.getInstance().getEquipoSeleccionado();

        textAreaHistoria.setText(equipo.getHistoria());
        labelJugador.setText(equipo.getNombre());

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numJugador"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        fotoColumn.setCellValueFactory(new PropertyValueFactory<>("foto"));

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
                                  //  newImageView.setImage(jugador.getImagenRoute());
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

        jugadoresObservable = FXCollections.observableArrayList(equipo.getJugadores());
        tableView.setItems(jugadoresObservable);

        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        estadisticasObservable = FXCollections.observableArrayList(
                new Estadistica("Color Primario (nuevo)", String.valueOf(equipo.getColorPrimario())),
                new Estadistica("Color Secundario: (nuevo)", String.valueOf(equipo.getColorSecundario())),
                new Estadistica("Carreras anotadas", String.valueOf(equipo.getCarrerasTotales())),
                new Estadistica("HIT totales", String.valueOf(equipo.getHitsTotales())),
                new Estadistica("Jonrones totales", String.valueOf(equipo.getJonronesTotales())),
                new Estadistica("Promedio de bateo", String.valueOf(equipo.getPromedioBateo())),
                new Estadistica("ERA", String.valueOf(equipo.getERA()))
        );

        valorColumn.setCellFactory(new Callback<TableColumn<Estadistica, String>, TableCell<Estadistica, String>>() {
            @Override
            public TableCell<Estadistica, String> call(TableColumn<Estadistica, String> param) {
                return new TableCell<Estadistica, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else if (item.startsWith("0x")) {
                            try {
                                Color color = Color.web(item);
                                Rectangle rect = new Rectangle(50, 15, color);
                                rect.setStroke(Color.BLACK); // contorno
                                setText(null);
                                setGraphic(rect);
                            } catch (IllegalArgumentException e) {
                                setText(item);
                                setGraphic(null);
                            }
                        } else {
                            setText(item);
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        tableView2.setItems(estadisticasObservable);
    }

    public void setEquipo(Equipo selectedTeam) {
    }

    public static class Estadistica {
        private final String categoria;
        private final String valor;

        public Estadistica(String categoria, String valor) {
            this.categoria = categoria;
            this.valor = valor;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getValor() {
            return valor;
        }
    }
}