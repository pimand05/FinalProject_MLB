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
import logic.Jugador;
import utility.Paths;

public class ControllerJugadores {

    @FXML
    public Button btnCrearJugador;
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


    /*void search(ActionEvent event) {
        tableView.setItems(searchList(searchBar.getText()));
    }*/

    /*private ObservableList<Jugador> searchList(String text) {
        ObservableList<Jugador> filteredList = FXCollections.observableArrayList();
        for (Jugador jugador : getPlayers()) {
            if (String.valueOf(player.getNumber()).contains(text) || player.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(player);
            }
        }
        return filteredList;
    }*/

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        //tableView.setItems(getPlayers());
    }

    public void openCrearJugador(ActionEvent actionEvent) {
        AppMain.app.loadStage(new Stage(), Paths.REGJUGADOR, "Crear Jugador", false, Paths.ICONMAIN);
    }
}