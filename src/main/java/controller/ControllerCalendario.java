package controller;

import application.AppMain;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.Equipo;
import logic.Partido;
import logic.SerieMundial;
import logic.Temporada;
import utility.Paths;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static utility.Paths.SIMULADOR;
import static utility.Paths.TABLAPOSICIONES;

public class ControllerCalendario {
   @FXML private GridPane calendarioGrid;
   @FXML private TableView<Partido> partidosTable;
   @FXML private DatePicker filtroFecha;
   @FXML private ComboBox<Equipo> filtroEquipo;
   @FXML private TableColumn<Partido, String> colLocal;
   @FXML private TableColumn<Partido, String> colVisitante;
   @FXML private Button btnMesAnterior;
   @FXML private Button btnMesSiguiente;
   @FXML private Button btnJugar;
   @FXML private Button btnTablaPosiciones;
   @FXML private Button btnIniciarTemp;
   @FXML private SplitPane splitPane;

   private Temporada temporada;
   private ObservableList<Partido> todosPartidos;
   private FilteredList<Partido> partidosFiltrados;
   private Map<LocalDate, List<Partido>> partidosPorFecha = new HashMap<>();
   private LocalDate fechaActual = LocalDate.now();

   SerieMundial serie = SerieMundial.getInstance();

   private static final Logger LOGGER = Logger.getLogger(ControllerCalendario.class.getName());

   public void initialize() {
      try {
         Temporada temporadaActual = serie.getTemporadaActual();
         configurarUI();
         if (temporadaActual != null && !temporadaActual.getPartidos().isEmpty()) {
            setTemporada(temporadaActual);
         } else {

            LOGGER.info("Inicializando vista sin temporada");
         }

         configurarBotonJugar();
         actualizarEstadoBotones();
         mostrarTablaPosiciones();

         LOGGER.info("Inicialización completada exitosamente");
      } catch (Exception e) {
         LOGGER.severe("Error durante la inicialización: " + e.getMessage());
         e.printStackTrace();
         mostrarAlerta("Error al cargar el calendario: " + e.getMessage());
      }
   }

   private void configurarUI() {

      btnMesAnterior.setOnAction(e -> cambiarMes(-1));
      btnMesSiguiente.setOnAction(e -> cambiarMes(1));

      filtroEquipo.setCellFactory(column -> new ListCell<>() {
         @Override
         protected void updateItem(Equipo equipo, boolean empty) {
            super.updateItem(equipo, empty);
            setText(empty || equipo == null ? "" : equipo.getNombre());
         }
      });

      filtroEquipo.setButtonCell(new ListCell<>() {
         @Override
         protected void updateItem(Equipo equipo, boolean empty) {
            super.updateItem(equipo, empty);
            setText(empty || equipo == null ? "" : equipo.getNombre());
         }
      });

      colLocal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipoLocal().getNombre()));
      colVisitante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipoVisitante().getNombre()));

      configurarFiltros();
   }

   private void configurarBotonIniciarTemporada() {
      btnIniciarTemp.setOnAction(e -> {
         Temporada nuevaTemporada = serie.iniciarNuevaTemporada(LocalDate.now());
         setTemporada(nuevaTemporada);
         actualizarEstadoBotones();
      });

      actualizarEstadoBotones();
   }


   private void configurarBotonJugar() {
      btnJugar.setOnAction(e -> {
         LocalDate hoy = LocalDate.now();
         List<Partido> partidosHoy = partidosPorFecha.get(hoy);

         if (partidosHoy != null && !partidosHoy.isEmpty()) {
            Partido partido = partidosHoy.get(0);
            if (!partido.isPartidoTerminado()) {
               abrirSimuladorPartido(partido);
            } else {
               mostrarResultadosPartido(partido);
            }
         } else {
            mostrarAlerta("No hay partidos programados para hoy");
         }
      });

   }


   private void mostrarResultadosPartido(Partido partido) {
      Dialog<Void> dialog = new Dialog<>();
      dialog.setTitle("Resultados del Partido");

      Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
      stage.getIcons().add(new Image(Paths.ICONMAIN));

      dialog.getDialogPane().setPrefSize(300, 100);

      dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

      VBox contenido = new VBox(10);
      contenido.setPadding(new Insets(15));
      contenido.setAlignment(Pos.CENTER_LEFT);

      Label titulo = new Label(partido.getEquipoLocal().getNombre() + " vs " + partido.getEquipoVisitante().getNombre());
      titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

      Label resultado = new Label("Resultado: " + partido.getCarrerasLocal() + " - " + partido.getCarrerasVisitante());
      resultado.setStyle("-fx-font-size: 14px;");

      contenido.getChildren().addAll(titulo, resultado);

      dialog.getDialogPane().setContent(contenido);

      dialog.showAndWait();
   }


   private void actualizarEstadoBotones() {
      boolean hayTemporada = temporada != null && !temporada.getPartidos().isEmpty();
      btnIniciarTemp.setDisable(hayTemporada);
      actualizarEstadoBotonJugar();
   }

   private void actualizarEstadoBotonJugar() {
      if (temporada == null || temporada.getPartidos().isEmpty()) {
         btnJugar.setDisable(true);
         btnJugar.setText("Sin partidos");
         return;
      }

      LocalDate hoy = LocalDate.now();
      List<Partido> partidosHoy = partidosPorFecha.get(hoy);

      if (partidosHoy == null || partidosHoy.isEmpty()) {
         btnJugar.setDisable(true);
         btnJugar.setText("Sin partidos");
      } else {
         Partido partido = partidosHoy.get(0);
         if (partido.isPartidoTerminado()) {
            btnJugar.setDisable(false);
            btnJugar.setText("Ver Resultados");
         } else {
            btnJugar.setDisable(false);
            btnJugar.setText("Jugar Partido");
         }
      }
   }


   public void setTemporada(Temporada temporada) {
      this.temporada = temporada;

      if (temporada != null) {
         this.todosPartidos = FXCollections.observableArrayList(temporada.getPartidos());
         this.partidosFiltrados = new FilteredList<>(todosPartidos);
         partidosTable.setItems(partidosFiltrados);

         Set<Equipo> equiposUnicosSet = new HashSet<>();
         for (Partido partido : temporada.getPartidos()) {
            equiposUnicosSet.add(partido.getEquipoLocal());
            equiposUnicosSet.add(partido.getEquipoVisitante());
         }

         filtroEquipo.setItems(FXCollections.observableArrayList(new ArrayList<>(equiposUnicosSet)));

         generarVistaCalendario();
         actualizarEstadoBotones();

      } else {
         this.todosPartidos = FXCollections.observableArrayList();
         this.partidosFiltrados = new FilteredList<>(todosPartidos);
         partidosTable.setItems(partidosFiltrados);
         filtroEquipo.setItems(FXCollections.observableArrayList());
      }

      generarVistaCalendario();
      actualizarEstadoBotones();
   }


   private void configurarFiltros() {
      filtroFecha.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
      filtroEquipo.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
   }

   private void aplicarFiltros() {
      Predicate<Partido> filtro = p -> true;

      if (filtroFecha.getValue() != null) {
         LocalDate fechaFiltro = filtroFecha.getValue();
         filtro = filtro.and(p -> p.getFecha().equals(fechaFiltro));
      }

      if (filtroEquipo.getValue() != null) {
         Equipo equipoFiltro = filtroEquipo.getValue();
         filtro = filtro.and(p ->
               p.getEquipoLocal().equals(equipoFiltro) ||
                     p.getEquipoVisitante().equals(equipoFiltro));
      }

      partidosFiltrados.setPredicate(filtro);
   }

   @FXML
   private void handleLimpiarFiltros() {
      filtroFecha.setValue(null);
      filtroEquipo.setValue(null);
      aplicarFiltros();
   }

   @FXML
   private void mostrarTablaPosiciones() {
      btnTablaPosiciones.setOnAction(e -> abrirTablaPosiciones());
   }

   private void generarVistaCalendario() {
      calendarioGrid.getChildren().clear();
      calendarioGrid.getColumnConstraints().clear();
      calendarioGrid.getRowConstraints().clear();

      for (int i = 0; i < 7; i++) {
         ColumnConstraints column = new ColumnConstraints();
         column.setHalignment(HPos.CENTER);
         column.setPercentWidth(100 / 7.0);
         calendarioGrid.getColumnConstraints().add(column);
      }

      for (int i = 0; i < 6; i++) {
         RowConstraints row = new RowConstraints();
         row.setVgrow(Priority.ALWAYS);
         calendarioGrid.getRowConstraints().add(row);
      }

      String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
      for (int i = 0; i < 7; i++) {
         Label diaLabel = new Label(diasSemana[i]);
         diaLabel.getStyleClass().add("calendar-header");
         calendarioGrid.add(diaLabel, i, 0);
      }

      LocalDate hoy = LocalDate.now();
      LocalDate primerDiaMes = fechaActual.withDayOfMonth(1);
      int diasEnMes = fechaActual.lengthOfMonth();
      int diaSemanaInicio = primerDiaMes.getDayOfWeek().getValue() - 1;

      int fila = 1;
      int columna = diaSemanaInicio;

      for (int dia = 1; dia <= diasEnMes; dia++) {
         VBox celda = new VBox(3);
         celda.setPadding(new Insets(2));
         celda.setMaxHeight(Double.MAX_VALUE);
         celda.setMaxWidth(Double.MAX_VALUE);

         Label diaLabel = new Label(String.valueOf(dia));
         celda.getChildren().add(diaLabel);

         LocalDate fechaCelda = fechaActual.withDayOfMonth(dia);

         List<Partido> partidosDia = new ArrayList<>();
         if (temporada != null) {
            for (Partido partido : temporada.getPartidos()) {
               if (partido.getFecha().equals(fechaCelda)) {
                  partidosDia.add(partido);
               }
            }
         }

         partidosPorFecha.put(fechaCelda, partidosDia);

         if (fechaCelda.equals(hoy)) {
            celda.getStyleClass().add("calendar-cell-today");
            diaLabel.getStyleClass().add("calendar-day-today");
         } else {
            celda.getStyleClass().add("calendar-cell");
            diaLabel.getStyleClass().add("calendar-day");
         }

         for (Partido partido : partidosDia) {
            Label equiposLabel = new Label(
                  partido.getEquipoLocal().getNombre() + " vs " +
                        partido.getEquipoVisitante().getNombre());
            equiposLabel.getStyleClass().add("partido-label");
            celda.getChildren().add(equiposLabel);
         }

         calendarioGrid.add(celda, columna, fila);

         columna = (columna + 1) % 7;
         if (columna == 0) fila++;
      }
   }


   private void abrirSimuladorPartido(Partido partido) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource(SIMULADOR));
         Parent root = loader.load();

         ControllerSimulador controller = loader.getController();
         controller.inicializarPartido(partido);

         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Simulador de Partido");
         stage.show();
      } catch (IOException e) {
         System.err.println("Error al cargar el simulador: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private void abrirTablaPosiciones() {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource(TABLAPOSICIONES));
         Parent root = loader.load();

         ControllerTablaPosiciones controller = loader.getController();
         controller.actualizarTabla(serie.getEquipos());

         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Tabla de Posiciones");
         stage.show();
      } catch (IOException e) {
         System.err.println("Error al cargar la tabla de posiciones: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private void cambiarMes(int incremento) {
      fechaActual = fechaActual.plusMonths(incremento);
      generarVistaCalendario();
      actualizarEstadoBotonJugar();
   }

   public void goHome(MouseEvent mouseEvent) {
      AppMain.app.changeScene(AppMain.app.getStage(), Paths.MAIN, "SERIE MUNDIAL", true,0);
   }

   private void mostrarAlerta(String mensaje) {
      Alert alerta = new Alert(Alert.AlertType.INFORMATION);
      alerta.setTitle("Información");
      alerta.setHeaderText(null);
      alerta.setContentText(mensaje);
      alerta.showAndWait();
   }
}