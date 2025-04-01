package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import logic.Equipo;
import logic.Partido;
import logic.Temporada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

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

   private Temporada temporada;
   private ObservableList<Partido> todosPartidos;
   private FilteredList<Partido> partidosFiltrados;
   private LocalDate fechaActual = LocalDate.now();

   public void initialize() {
      // Configurar formato de fecha
      fechaActualLabel.setText(fechaActual.format(
            DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy")));

      // Configurar los botones para cambiar de mes
      btnMesAnterior.setOnAction(e -> cambiarMes(-1));
      btnMesSiguiente.setOnAction(e -> cambiarMes(1));

      // Configurar combo box de equipos
      filtroEquipo.setCellFactory(param -> new ListCell<Equipo>() {
         @Override protected void updateItem(Equipo equipo, boolean empty) {
            super.updateItem(equipo, empty);
            setText(empty || equipo == null ? "" : equipo.getNombre());
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
      filtroEquipo.setItems(FXCollections.observableArrayList(
            temporada.getPartidos().stream()
                  .flatMap(p -> List.of(p.getEquipoLocal(), p.getEquipoVisitante()).stream())
                  .distinct()
                  .toList()
      ));

      generarVistaCalendario();
   }

   private void configurarFiltros() {
      filtroFecha.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
      filtroEquipo.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
   }

   private void aplicarFiltros() {
      Predicate<Partido> filtro = p -> true;

      if (filtroFecha.getValue() != null) {
         filtro = filtro.and(p -> p.getFecha().equals(filtroFecha.getValue()));
      }

      if (filtroEquipo.getValue() != null) {
         Equipo equipo = filtroEquipo.getValue();
         filtro = filtro.and(p -> p.getEquipoLocal().equals(equipo) ||
               p.getEquipoVisitante().equals(equipo));
      }

      partidosFiltrados.setPredicate(filtro);
   }

   @FXML
   private void handleLimpiarFiltros() {
      filtroFecha.setValue(null);
      filtroEquipo.setValue(null);
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

      // Generar celdas del calendario
      LocalDate primerDiaMes = fechaActual.withDayOfMonth(1);
      int diasEnMes = fechaActual.lengthOfMonth();
      int diaSemanaInicio = primerDiaMes.getDayOfWeek().getValue() - 1; // Lunes=0

      int fila = 1;
      int columna = diaSemanaInicio;

      for (int dia = 1; dia <= diasEnMes; dia++) {
         VBox celda = new VBox(3);
         celda.getStyleClass().add("calendar-cell");
         celda.setPadding(new Insets(2));

         Label diaLabel = new Label(String.valueOf(dia));
         diaLabel.getStyleClass().add("calendar-day");
         celda.getChildren().add(diaLabel);

         LocalDate fechaCelda = fechaActual.withDayOfMonth(dia);
         List<Partido> partidosDia = temporada.getPartidos().stream()
               .filter(p -> p.getFecha().equals(fechaCelda))
               .toList();

         for (Partido partido : partidosDia) {
            Button btnPartido = new Button(
                  partido.getEquipoLocal().getNombre() + " vs " +
                        partido.getEquipoVisitante().getNombre());

            btnPartido.getStyleClass().add("partido-button");
            btnPartido.setUserData(partido);
            btnPartido.setOnAction(e -> {
               partidosTable.getSelectionModel().select(partido);
               partidosTable.scrollTo(partido);
            });

            celda.getChildren().add(btnPartido);
         }

         calendarioGrid.add(celda, columna, fila);

         columna = (columna + 1) % 7;
         if (columna == 0) fila++;
      }
   }

   private void cambiarMes(int incremento) {
      // Cambiar el mes sumando o restando el valor de incremento
      fechaActual = fechaActual.plusMonths(incremento);
      generarVistaCalendario();
   }
}