package controller;

import application.AppMain;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.*;
import utility.Paths;

import java.net.URL;
import java.util.*;

public class ControllerDashboard implements Initializable {

    String[] coloresPastel = {
            "#1d3a50", // azul muy oscuro
            "#305d7e", // azul oscuro
            "#507ea4", // azul grisáceo medio-oscuro
            "#6c7f8d", // gris azulado medio
            "#aab8c2"  // gris azulado claro
    };

    @FXML private BarChart<Number, String> graficaAVG;
    @FXML private StackedBarChart<String, Number> graficaRG;
    @FXML private PieChart graficaVictorias;

    @FXML private StackPane topImage1HIT;
    @FXML private StackPane topImage1HR;
    @FXML private StackPane topImage2HIT;
    @FXML private StackPane topImage2HR;
    @FXML private StackPane topImage3HIT;
    @FXML private StackPane topImage3HR;
    @FXML private StackPane topImage4HIT;
    @FXML private StackPane topImage4HR;
    @FXML private StackPane topImage5HIT;
    @FXML private StackPane topImage5HR;

    @FXML private Label topPlayer1ERA;
    @FXML private Label topPlayer1HIT;
    @FXML private Label topPlayer1HR;
    @FXML private Label topPlayer2ERA;
    @FXML private Label topPlayer2HIT;
    @FXML private Label topPlayer2HR;
    @FXML private Label topPlayer3ERA;
    @FXML private Label topPlayer3HIT;
    @FXML private Label topPlayer3HR;
    @FXML private Label topPlayer4ERA;
    @FXML private Label topPlayer4HIT;
    @FXML private Label topPlayer4HR;
    @FXML private Label topPlayer5ERA;
    @FXML private Label topPlayer5HIT;
    @FXML private Label topPlayer5HR;

    @FXML private Label topResult1ERA;
    @FXML private Label topResult1HIT;
    @FXML private Label topResult1HR;
    @FXML private Label topResult2ERA;
    @FXML private Label topResult2HIT;
    @FXML private Label topResult2HR;
    @FXML private Label topResult3ERA;
    @FXML private Label topResult3HIT;
    @FXML private Label topResult3HR;
    @FXML private Label topResult4ERA;
    @FXML private Label topResult4HIT;
    @FXML private Label topResult4HR;
    @FXML private Label topResult5ERA;
    @FXML private Label topResult5HIT;
    @FXML private Label topResult5HR;

    @FXML private StackPane topImage1ERA;
    @FXML private StackPane topImage2ERA;
    @FXML private StackPane topImage3ERA;
    @FXML private StackPane topImage4ERA;
    @FXML private StackPane topImage5ERA;

    @FXML private Text txtDia;
    @FXML private Text txtMes;
    @FXML private Text txtYear;

    @FXML
    void openHome(MouseEvent event) {
        AppMain.app.changeScene(AppMain.app.getStage(), Paths.MAIN, "SERIE MUNDIAL", true, 0);
    }

    @FXML
    void openStats(MouseEvent event) {

    }

    private void setJugadoresHR(){
        ArrayList<Bateador> bateadores = SerieMundial.getInstance().getBateadores();
        bateadores.sort(Comparator.comparing((Bateador b) -> b.getStats().getHomeRuns()).reversed());

        // Tomar el top 5
        List<Bateador> top5 = bateadores.subList(0, Math.min(5, bateadores.size()));
        int i = 1;
        for (Bateador bateador : top5) {
            setPosicionHR(bateador, i);
            i++;
        }
    }

    private void setJugadoresHIT() {
        ArrayList<Bateador> bateadores = SerieMundial.getInstance().getBateadores();
        bateadores.sort(Comparator.comparing((Bateador b) -> b.getStats().getHits()).reversed());

        // Tomar el top 5
        List<Bateador> top5 = bateadores.subList(0, Math.min(5, bateadores.size()));
        int i = 1;
        for (Bateador bateador : top5) {
            setPosicionHIT(bateador, i);
            i++;
        }
    }

    private void setPitchersERA() {
        ArrayList<Pitcher> pitchers = SerieMundial.getInstance().getPitchers();
        // Ordenar bateadores por promedio de bateo (de mayor a menor)
        pitchers.sort(Comparator.comparing((Pitcher p) -> p.getStats().calcularERA()).reversed());

        // Tomar el top 5
        List<Pitcher> top5 = pitchers.subList(0, Math.min(5, pitchers.size()));
        int i = 1;
        for (Pitcher pitcher : top5) {
            setPosicionERA(pitcher, i);
            i++;
        }
    }

    private void setPosicionHR(Bateador bateador, int posicion) {
        String estiloImagen = "-fx-background-image: url('" + bateador.getImagenRoute() + "');";

        switch (posicion) {
            case 1:
                topPlayer1HR.setText(bateador.getNombre());
                topResult1HR.setText(String.valueOf(bateador.getStats().getHomeRuns()));
                topImage1HR.setStyle(estiloImagen);
                break;
            case 2:
                topPlayer2HR.setText(bateador.getNombre());
                topResult2HR.setText(String.valueOf(bateador.getStats().getHomeRuns()));
                topImage2HR.setStyle(estiloImagen);
                break;
            case 3:
                topPlayer3HR.setText(bateador.getNombre());
                topResult3HR.setText(String.valueOf(bateador.getStats().getHomeRuns()));
                topImage3HR.setStyle(estiloImagen);
                break;
            case 4:
                topPlayer4HR.setText(bateador.getNombre());
                topResult4HR.setText(String.valueOf(bateador.getStats().getHomeRuns()));
                topImage4HR.setStyle(estiloImagen);
                break;
            case 5:
                topPlayer5HR.setText(bateador.getNombre());
                topResult5HR.setText(String.valueOf(bateador.getStats().getHomeRuns()));
                topImage5HR.setStyle(estiloImagen);
                break;
        }
    }

    private void setPosicionERA(Pitcher pitcher, int posicion) {
        String estiloImagen = "-fx-background-image: url('" + pitcher.getImagenRoute() + "');";

        switch (posicion) {
            case 1:
                topPlayer1ERA.setText(pitcher.getNombre());
                topResult1ERA.setText(String.valueOf(pitcher.getStats().calcularERA()));
                topImage1ERA.setStyle(estiloImagen);
                break;
            case 2:
                topPlayer2ERA.setText(pitcher.getNombre());
                topResult2ERA.setText(String.valueOf(pitcher.getStats().calcularERA()));
                topImage2ERA.setStyle(estiloImagen);
                break;
            case 3:
                topPlayer3ERA.setText(pitcher.getNombre());
                topResult3ERA.setText(String.valueOf(pitcher.getStats().calcularERA()));
                topImage3ERA.setStyle(estiloImagen);
                break;
            case 4:
                topPlayer4ERA.setText(pitcher.getNombre());
                topResult4ERA.setText(String.valueOf(pitcher.getStats().calcularERA()));
                topImage4ERA.setStyle(estiloImagen);
                break;
            case 5:
                topPlayer5ERA.setText(pitcher.getNombre());
                topResult5ERA.setText(String.valueOf(pitcher.getStats().calcularERA()));
                topImage5ERA.setStyle(estiloImagen);
                break;
        }
    }

    private void setPosicionHIT(Bateador bateador, int posiciones) {
        String estiloImagen = "-fx-background-image: url('" + bateador.getImagenRoute() + "');";

        switch (posiciones) {
            case 1 -> {
                topPlayer1HIT.setText(bateador.getNombre());
                topResult1HIT.setText(String.valueOf(bateador.getStats().getHits()));
                topImage1HIT.setStyle(estiloImagen);
            }
            case 2 -> {
                topPlayer2HIT.setText(bateador.getNombre());
                topResult2HIT.setText(String.valueOf(bateador.getStats().getHits()));
                topImage2HIT.setStyle(estiloImagen);
            }
            case 3 -> {
                topPlayer3HIT.setText(bateador.getNombre());
                topResult3HIT.setText(String.valueOf(bateador.getStats().getHits()));
                topImage3HIT.setStyle(estiloImagen);
            }
            case 4 -> {
                topPlayer4HIT.setText(bateador.getNombre());
                topResult4HIT.setText(String.valueOf(bateador.getStats().getHits()));
                topImage4HIT.setStyle(estiloImagen);
            }
            case 5 -> {
                topPlayer5HIT.setText(bateador.getNombre());
                topResult5HIT.setText(String.valueOf(bateador.getStats().getHits()));
                topImage5HIT.setStyle(estiloImagen);
            }
        }
    }

    private void setGraficaAVG() {
        ArrayList<Bateador> bateadores = SerieMundial.getInstance().getBateadores();
        // Ordenar bateadores por promedio de bateo (de mayor a menor)
        bateadores.sort(Comparator.comparing((Bateador b) -> b.getStats().getPromedioBateo()).reversed());

        // Tomar el top 5
        List<Bateador> top5 = bateadores.subList(0, Math.min(5, bateadores.size()));
        Collections.reverse(top5);

        // Crear la serie de datos para la gráfica
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Bateador bateador : top5) {
            XYChart.Data<Number, String> data = new XYChart.Data<>(bateador.getStats().calcularPromedioBateo(), bateador.getNombre());
            series.getData().add(data);

            Label label = new Label(String.valueOf(bateador.getStats().calcularPromedioBateo()));
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

            // Agregar el label encima de la barra con animación
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode instanceof StackPane bar) {
                    // Agregamos el label como hijo del StackPane
                    bar.getChildren().add(label);
                    StackPane.setAlignment(label, Pos.CENTER_RIGHT);
                    label.setPadding(new Insets(0, 5, 0, 0));

                    // Animación al aparecer (opcional)
                    ScaleTransition st = new ScaleTransition(Duration.millis(800), bar);
                    st.setFromX(0);
                    st.setToX(1);
                    st.setInterpolator(Interpolator.EASE_OUT);
                    st.play();
                }
            });
        }

        // Mostrar en la gráfica
        graficaAVG.getData().clear();
        graficaAVG.getData().add(series);
        graficaAVG.setLegendVisible(false);
    }

    private void setGraficaRG() {
        ArrayList<Equipo> equipos = SerieMundial.getInstance().getEquipos();
        equipos.sort(Comparator.comparing((Equipo p) -> p.getRG()).reversed());

        List<Equipo> top5 = equipos.subList(0, Math.min(5, equipos.size()));
        Collections.reverse(top5);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Equipo equipo : top5) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(equipo.getNombre(),equipo.getRG());
            series.getData().add(data);

            Label label = new Label(String.valueOf(equipo.getRG()));
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");

            // Agregar el label encima de la barra con animación
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode instanceof StackPane bar) {
                    // Agregamos el label como hijo del StackPane
                    bar.getChildren().add(label);
                    StackPane.setAlignment(label, Pos.TOP_CENTER);
                    label.setPadding(new Insets(10, 5, 0, 0));

                    // Animación al aparecer (opcional)
                    ScaleTransition st = new ScaleTransition(Duration.millis(800), bar);
                    st.setFromX(0);
                    st.setToX(1);
                    st.setInterpolator(Interpolator.EASE_OUT);
                    st.play();
                }
            });
        }

        // Mostrar en la gráfica
        graficaRG.getData().clear();
        graficaRG.getData().add(series);
        graficaRG.setLegendVisible(false);
    }

    private void setGraficaVictorias() {
        ObservableList<PieChart.Data> graficaVictory = FXCollections.observableArrayList();
        ArrayList<Equipo> equipos = SerieMundial.getInstance().getEquipos();

        equipos.sort(Comparator.comparing(Equipo::getJuegosGanados).reversed());
        List<Equipo> top5 = equipos.subList(0, Math.min(5, equipos.size()));
        for (Equipo equipo : top5) {
            graficaVictory.add(new PieChart.Data(equipo.getNombre(), equipo.getJuegosGanados()));
        }
        graficaVictorias.setData(graficaVictory);

        // Aplicar colores a las secciones del gráfico
        for (int i = 0; i < graficaVictory.size(); i++) {
            PieChart.Data data = graficaVictory.get(i);
            String color = coloresPastel[i % coloresPastel.length];

            // Esperar a que el nodo esté listo antes de aplicar el color
            int finalI = i;
            Platform.runLater(() -> data.getNode().setStyle("-fx-pie-color: " + color + ";"));
        }
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Los meses en Calendar son 0-11
        txtDia.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        txtMes.setText(getMonth(month));
        txtYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
    }

    private String getMonth(int month) {
        return switch (month) {
            case 1 -> " de Enero del ";
            case 2 -> " de Febrero del ";
            case 3 -> " de Marzo del ";
            case 4 -> " de Abril del ";
            case 5 -> " de Mayo del ";
            case 6 -> " de Junio del ";
            case 7 -> " de Julio del ";
            case 8 -> " de Agosto del ";
            case 9 -> " de Septiembre del ";
            case 10 -> " de Octubre del ";
            case 11 -> " de Noviembre del ";
            case 12 -> " de Diciembre del ";
            default -> "";
        };
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGraficaAVG();
        setJugadoresHR();
        setJugadoresHIT();
        setPitchersERA();
        setDate();
        setGraficaVictorias();
        setGraficaRG();
    }
}
