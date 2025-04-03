package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.Equipo;
import logic.Partido;
import logic.SerieMundial;
import logic.Temporada;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

import static utility.Paths.SIMULADOR;
import static utility.Paths.TABLAPOSICIONES;

public class ControllerCalendario {
   @FXML private GridPane calendarioGrid;
   @FXML private TableView<Partido> partidosTable;
   @FXML private DatePicker filtroFecha;
   @FXML private ComboBox<Equipo> filtroEquipo;
   @FXML private Label fechaActualLabel;
   @FXML private TableColumn<Partido, String> colLocal;
   @FXML private TableColumn<Partido, String> colVisitante;
   @FXML private Button btnMesAnterior;
   @FXML private Button btnMesSiguiente;
   @FXML private Button btnJugar;
   @FXML private Button btnTablaPosiciones;

   private Temporada temporada;
   private ObservableList<Partido> todosPartidos;
   private FilteredList<Partido> partidosFiltrados;
   private Map<LocalDate, List<Partido>> partidosPorFecha = new HashMap<>();
   private LocalDate fechaActual = LocalDate.now();

   SerieMundial serie = SerieMundial.getInstance();

   public void initialize() {
      // Configurar formato de fecha
      fechaActualLabel.setText(fechaActual.format(
            DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")));

      // Configurar los botones para cambiar de mes
      btnMesAnterior.setOnAction(e -> cambiarMes(-1));
      btnMesSiguiente.setOnAction(e -> cambiarMes(1));

      // Configurar combo box de equipos
      filtroEquipo.setCellFactory(new Callback<ListView<Equipo>, ListCell<Equipo>>() {
         @Override
         public ListCell<Equipo> call(ListView<Equipo> param) {
            return new ListCell<Equipo>() {
               @Override
               protected void updateItem(Equipo equipo, boolean empty) {
                  super.updateItem(equipo, empty);
                  if (empty || equipo == null) {
                     setText("");
                  } else {
                     setText(equipo.getNombre());
                  }
               }
            };
         }
      });

      filtroEquipo.setButtonCell(new ListCell<Equipo>() {
         @Override protected void updateItem(Equipo equipo, boolean empty) {
            super.updateItem(equipo, empty);
            setText(empty || equipo == null ? "" : equipo.getNombre());
         }
      });

      colLocal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Partido, String>, ObservableValue<String>>() {
         @Override
         public ObservableValue<String> call(TableColumn.CellDataFeatures<Partido, String> cellData) {
            return new SimpleStringProperty(cellData.getValue().getEquipoLocal().getNombre());
         }
      });

      colVisitante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Partido, String>, ObservableValue<String>>() {
         @Override
         public ObservableValue<String> call(TableColumn.CellDataFeatures<Partido, String> cellData) {
            return new SimpleStringProperty(cellData.getValue().getEquipoVisitante().getNombre());
         }
      });
      // Configurar listeners para filtros
      configurarFiltros();
   }

   public void setTemporada(Temporada temporada) {
      this.temporada = temporada;
      this.todosPartidos = FXCollections.observableArrayList(temporada.getPartidos());
      this.partidosFiltrados = new FilteredList<>(todosPartidos);

      partidosTable.setItems(partidosFiltrados);

      List<Equipo> equiposUnicos = new ArrayList<>();

      for (Partido partido : temporada.getPartidos()) {
         if (!equiposUnicos.contains(partido.getEquipoLocal())) {
            equiposUnicos.add(partido.getEquipoLocal());
         }
         if (!equiposUnicos.contains(partido.getEquipoVisitante())) {
            equiposUnicos.add(partido.getEquipoVisitante());
         }
      }

      filtroEquipo.setItems(FXCollections.observableArrayList(equiposUnicos));

      generarVistaCalendario();
   }

   private void configurarFiltros() {
      filtroFecha.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
      filtroEquipo.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
   }

   private void aplicarFiltros() {
      Predicate<Partido> filtro = new Predicate<Partido>() {
         @Override
         public boolean test(Partido p) {
            return true;
         }
      };

      // Filtro por fecha
      if (filtroFecha.getValue() != null) {
         final LocalDate fechaFiltro = filtroFecha.getValue();
         Predicate<Partido> filtroFechaPredicate = new Predicate<Partido>() {
            @Override
            public boolean test(Partido p) {
               return p.getFecha().equals(fechaFiltro);
            }
         };
         filtro = filtro.and(filtroFechaPredicate);
      }

      // Filtro por equipo
      if (filtroEquipo.getValue() != null) {
         final Equipo equipoFiltro = filtroEquipo.getValue();
         Predicate<Partido> filtroEquipoPredicate = new Predicate<Partido>() {
            @Override
            public boolean test(Partido p) {
               return p.getEquipoLocal().equals(equipoFiltro) ||
                     p.getEquipoVisitante().equals(equipoFiltro);
            }
         };
         filtro = filtro.and(filtroEquipoPredicate);
      }

      partidosFiltrados.setPredicate(filtro);
   }

   @FXML
   private void handleLimpiarFiltros() {
      filtroFecha.setValue(null);
      filtroEquipo.setValue(null);
   }

   @FXML
   private void iniciarSimulador() {
      btnJugar.setOnAction(e -> {
         LocalDate hoy = LocalDate.now();
         List<Partido> partidosHoy = partidosPorFecha.get(hoy);

         if (partidosHoy != null && !partidosHoy.isEmpty()) {
            Partido partidoAHoy = partidosHoy.get(0);  // O usa selección del usuario
            abrirSimuladorPartido(partidoAHoy);
         }
      });
   }

   @FXML
   private void mostrarTablaPosiciones(){
      btnTablaPosiciones.setOnAction(e -> {
         abrirTablaPosiciones();
      });
   }


   private void generarVistaCalendario() {
      calendarioGrid.getChildren().clear();
      calendarioGrid.setMaxWidth(250);
      calendarioGrid.setMaxHeight(200);

      // Cabecera con días de la semana
      String[] diasSemana = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
      for (int i = 0; i < 7; i++) {
         Label diaLabel = new Label(diasSemana[i]);
         diaLabel.getStyleClass().add("calendar-header");
         calendarioGrid.add(diaLabel, i, 0);
      }

      LocalDate hoy = LocalDate.now();
      LocalDate primerDiaMes = fechaActual.withDayOfMonth(1);
      int diasEnMes = fechaActual.lengthOfMonth();
      int diaSemanaInicio = primerDiaMes.getDayOfWeek().getValue() - 1; // Lunes=0

      int fila = 1;
      int columna = diaSemanaInicio;

      for (int dia = 1; dia <= diasEnMes; dia++) {
         VBox celda = new VBox(3);
         celda.setPadding(new Insets(2));

         Label diaLabel = new Label(String.valueOf(dia));
         celda.getChildren().add(diaLabel);

         LocalDate fechaCelda = fechaActual.withDayOfMonth(dia);

         List<Partido> partidosDia = new ArrayList<>();
         for (Partido partido : temporada.getPartidos()) {
            if (partido.getFecha().equals(fechaCelda)) {
               partidosDia.add(partido);
            }
         }

         partidosPorFecha.put(fechaCelda, partidosDia);

         // Resaltar el día actual
         if (fechaCelda.equals(hoy)) {
            celda.getStyleClass().add("calendar-cell-today");
            diaLabel.getStyleClass().add("calendar-day-today");
         } else {
            celda.getStyleClass().add("calendar-cell");
            diaLabel.getStyleClass().add("calendar-day");
         }

         // Mostrar partidos (solo información, sin botones)
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
         // Manejo más detallado del error
         System.err.println("Error al cargar el simulador: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private void abrirTablaPosiciones() {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource(TABLAPOSICIONES));
         Parent root = loader.load();

         ControllerTablaPosiciones controller = loader.getController();

         List<Equipo> equipos = serie.getEquipos();

         controller.actualizarTabla(equipos);

         Stage stage = new Stage();
         stage.setScene(new Scene(root));
         stage.setTitle("Tabla de Posiciones");
         stage.show();
      } catch (IOException e) {
         System.err.println("Error al cargar la tabla de posiciones: " + e.getMessage());
         e.printStackTrace();
      }
   }

   private ArrayList<Partido> filtrarPartidosHoy() {
      ArrayList<Partido> partidosHoy = new ArrayList<>();
      LocalDate hoy = LocalDate.now();

      for (Partido partido : temporada.getPartidos()) {
         if (partido.getFecha().equals(hoy)) {
            partidosHoy.add(partido);
         }
      }

      return partidosHoy;
   }

   private void cambiarMes(int incremento) {
      // Cambiar el mes sumando o restando el valor de incremento
      fechaActual = fechaActual.plusMonths(incremento);
      generarVistaCalendario();
   }
}