package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private TableView<Estadistica> tableView2;

    @FXML
    private TableColumn<Estadistica, String> categoriaColumn;

    @FXML
    private TableColumn<Estadistica, String> valorColumn;

    private ObservableList<Jugador> jugadoresObservable;
    private ObservableList<Estadistica> estadisticasObservable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Equipo equipo = SerieMundial.getInstance().getEquipoSeleccionado();

        textAreaHistoria.setText(equipo.getHistoria());

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("numJugador"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("posicion"));

        jugadoresObservable = FXCollections.observableArrayList(equipo.getJugadores());
        tableView.setItems(jugadoresObservable);

        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        estadisticasObservable = FXCollections.observableArrayList(
                new Estadistica("Record", String.valueOf(equipo.getJuegosGanados())),
                new Estadistica("Carreras anotadas", String.valueOf(equipo.getCarrerasTotales())),
                new Estadistica("Jonrones totales", String.valueOf(equipo.getCarrerasTotales())),
                new Estadistica("Promedio de bateo", String.valueOf(equipo.getHitsTotales())),
                new Estadistica("OBP", String.valueOf(equipo.getJuegosGanados())),
                new Estadistica("SLG (Slugging)", String.valueOf(equipo.getJuegosPerdidos())),
                new Estadistica("ERA", String.valueOf(equipo.getCarrerasTotales())),
                new Estadistica("WHIP", String.valueOf(equipo.getHitsTotales()))
        );
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