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

    /*private ObservableList<Player> getPlayers() {
        return FXCollections.observableArrayList(
                new Player(1, "Brandon Nimmo", "CF", "New York Mets"),
                new Player(2, "Francisco Lindor", "SS", "New York Mets"),
                new Player(3, "Pete Alonso", "1B", "New York Mets"),
                new Player(4, "Jeff McNeil", "2B", "New York Mets"),
                new Player(5, "Starling Marte", "RF", "New York Mets"),
                new Player(6, "Mark Canha", "LF", "New York Mets"),
                new Player(7, "Eduardo Escobar", "3B", "New York Mets"),
                new Player(8, "James McCann", "C", "New York Mets"),
                new Player(9, "Jacob deGrom", "P", "New York Mets"),
                new Player(10, "Max Scherzer", "P", "New York Mets"),
                new Player(11, "Chris Bassitt", "P", "New York Mets"),
                new Player(12, "Taijuan Walker", "P", "New York Mets"),
                new Player(13, "Carlos Carrasco", "P", "New York Mets"),
                new Player(14, "Edwin Diaz", "P", "New York Mets"),
                new Player(15, "Trevor May", "P", "New York Mets"),
                new Player(16, "Seth Lugo", "P", "New York Mets"),
                new Player(17, "Drew Smith", "P", "New York Mets"),
                new Player(18, "Joely Rodriguez", "P", "New York Mets"),
                new Player(19, "Adam Ottavino", "P", "New York Mets"),
                new Player(20, "David Peterson", "P", "New York Mets"),
                new Player(21, "Tomas Nido", "C", "New York Mets"),
                new Player(22, "Luis Guillorme", "INF", "New York Mets"),
                new Player(23, "Dominic Smith", "1B", "New York Mets"),
                new Player(24, "J.D. Davis", "3B", "New York Mets"),
                new Player(25, "Travis Jankowski", "OF", "New York Mets"),
                new Player(26, "Patrick Mazeika", "C", "New York Mets")
        );
    }*/
}