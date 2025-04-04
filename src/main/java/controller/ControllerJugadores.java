package controller;

import application.AppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
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
    private TextField searchBar;

    private ObservableList<Jugador> jugadoresObservable;

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));

        jugadoresObservable = FXCollections.observableArrayList(SerieMundial.getInstance().getJugadores());
        tableView.setItems(jugadoresObservable);
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
        AppMain.app.loadStage(new Stage(), Paths.REGJUGADOR, "Crear Jugador", false, Paths.ICONMAIN);
    }
}
