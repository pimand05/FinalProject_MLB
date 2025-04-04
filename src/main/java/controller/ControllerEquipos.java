package controller;

import application.AppMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Equipo;
import utility.Paths;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerEquipos implements Initializable {
    @FXML
    public Button btnCrearEquipo;

    /*private final ObservableList<Team> equipos = FXCollections.observableArrayList(
            new Team("Yankees", "New York", "Yankee Stadium"), new Team("Mets", "New York", "Citi Field"),
            new Team("Red Sox", "Boston", "Fenway Park"), new Team("Giants", "San Francisco", "Oracle Park"),
            new Team("Cowboys", "Dallas", "AT&T Stadium"), new Team("Packers", "Green Bay", "Lambeau Field"),
            new Team("Bears", "Chicago", "Soldier Field"), new Team("Lakers", "Los Angeles", "Staples Center"),
            new Team("Celtics", "Boston", "TD Garden"), new Team("Heat", "Miami", "FTX Arena"),
            new Team("Warriors", "San Francisco", "Chase Center"), new Team("Bulls", "Chicago", "United Center"),
            new Team("Clippers", "Los Angeles", "Staples Center"), new Team("Dodgers", "Los Angeles", "Dodger Stadium"),
            new Team("Yankees", "New York", "Yankee Stadium"), new Team("Mets", "New York", "Citi Field"),
            new Team("Red Sox", "Boston", "Fenway Park"), new Team("Giants", "San Francisco", "Oracle Park")
    );*/

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Equipo> tableView;

    @FXML
    private TableColumn<Equipo, String> nameColumn;

    @FXML
    private TableColumn<Equipo, String> imageColumn;

    @FXML
    private TableColumn<Equipo, String> ciudadColumn;

    @FXML
    private TableColumn<Equipo, String> estadioColumn;

    @FXML
    /*void search(ActionEvent event) {
        tableView.setItems(searchList(searchBar.getText()));
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        ciudadColumn.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        estadioColumn.setCellValueFactory(new PropertyValueFactory<>("estadio"));

        //tableView.setItems(equipos);

        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleRowClick);
    }

    private void handleRowClick(MouseEvent event) {
        Equipo selectedTeam = tableView.getSelectionModel().getSelectedItem();
        if (selectedTeam != null) {
            System.out.println("Equipo seleccionado: " + selectedTeam.getNombre());
        }
    }

    public void openCrearEquipo(ActionEvent actionEvent) {
        AppMain.app.loadStage(new Stage(), Paths.RGEQUIPO, "Crear Equipo", false, Paths.ICONMAIN);
    }

    /*private ObservableList<Equipo> searchList(String searchWords) {
        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));
        ObservableList<Equipo> result = FXCollections.observableArrayList();

        for (Equipo team : equipos) {
            boolean matchesAllWords = true;

            for (String word : searchWordsArray) {
                if (!team.getName().toLowerCase().contains(word.toLowerCase())) {
                    matchesAllWords = false;
                    break;
                }
            }

            if (matchesAllWords) {
                result.add(team);
            }
        }
        return result;
    }*/

}
