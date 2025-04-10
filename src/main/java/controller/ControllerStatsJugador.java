package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.*;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class ControllerStatsJugador {
   // Paneles contenedores
   @FXML
   private Pane contenedorBateador;
   @FXML
   private Pane contenedorPitcher;
   @FXML
   private Pane paneBateador;
   @FXML
   private Pane panePitcher;

   // Componentes para bateador
   @FXML
   private Label nombreBateador;
   @FXML
   private Label infoBateador;
   @FXML
   private Label fechaNacBateador;
   @FXML
   private Label edadBateador;
   @FXML
   private Label alturaBateador;
   @FXML
   private Label avgBateador;
   @FXML
   private Label carrerasBateador;
   @FXML
   private Label homeRunsBateador;
   @FXML
   private TableView<BateadorStatsRow> tablaStatsBateador;
   @FXML
   private TableColumn<BateadorStatsRow, Integer> colJuegosBateador;
   @FXML
   private TableColumn<BateadorStatsRow, Integer> colHitsBateador;
   @FXML
   private TableColumn<BateadorStatsRow, Integer> colHomeRunsBateador;
   @FXML
   private TableColumn<BateadorStatsRow, Float> colAvgBateador;
   @FXML
   private ImageView fotoBateador;
   @FXML
   private ImageView logoEquipo;

   // Componentes para pitcher
   @FXML
   private Label nombrePitcher;
   @FXML
   private Label infoPitcher;
   @FXML
   private Label fechaNacimPit;
   @FXML
   private Label edadPitcher;
   @FXML
   private Label alturaPitcher;
   @FXML
   private Label labelEraPit;
   @FXML
   private Label labelPonchesPit;
   @FXML
   private Label labelCarrerasLimpias;
   @FXML
   private TableView<PitcherStatsRow> tablaStatsPitcher;
   @FXML
   private TableColumn<PitcherStatsRow, Integer> colJuegosPitcher;
   @FXML
   private TableColumn<PitcherStatsRow, Integer> colEntradasLan;
   @FXML
   private TableColumn<PitcherStatsRow, Integer> colPonchesLan;
   @FXML
   private TableColumn<PitcherStatsRow, Integer> colCarrerasLimp;
   @FXML
   private TableColumn<PitcherStatsRow, Float> colEfectivas;
   @FXML
   private ImageView fotoPitcher;
   @FXML
   private ImageView logoEquipoP;

   // Datos
   private ObservableList<BateadorStatsRow> statsBateadorData = FXCollections.observableArrayList();
   private ObservableList<PitcherStatsRow> statsPitcherData = FXCollections.observableArrayList();

   private Jugador jugador;
   SerieMundial serie = SerieMundial.getInstance();

   public void setJugador() {
      Jugador jugador = serie.getJugadorSeleccionado();
      mostrarJugador(jugador);
   }

   public void initialize() {
      Jugador jugador = serie.getJugadorSeleccionado();
      if (jugador != null) {
         mostrarJugador(jugador);
      }

      configurarTablas();
   }

   private void configurarTablas() {
      // Configurar tabla de bateador
      colJuegosBateador.setCellValueFactory(new PropertyValueFactory<>("juegos"));
      colHitsBateador.setCellValueFactory(new PropertyValueFactory<>("hits"));
      colHomeRunsBateador.setCellValueFactory(new PropertyValueFactory<>("hr"));
      colAvgBateador.setCellValueFactory(new PropertyValueFactory<>("avg"));

      colAvgBateador.setCellFactory(new Callback<TableColumn<BateadorStatsRow, Float>, TableCell<BateadorStatsRow, Float>>() {
         @Override
         public TableCell<BateadorStatsRow, Float> call(TableColumn<BateadorStatsRow, Float> column) {
            return new TableCell<BateadorStatsRow, Float>() {
               @Override
               protected void updateItem(Float value, boolean empty) {
                  super.updateItem(value, empty);
                  if (empty || value == null) {
                     setText("");
                  } else {
                     setText(String.format("%.2f", value));
                  }
               }
            };
         }
      });

      // Configurar tabla de pitcher
      colJuegosPitcher.setCellValueFactory(new PropertyValueFactory<>("juegos"));
      colEntradasLan.setCellValueFactory(new PropertyValueFactory<>("ip"));
      colPonchesLan.setCellValueFactory(new PropertyValueFactory<>("ponches"));
      colEfectivas.setCellValueFactory(new PropertyValueFactory<>("era"));
      colCarrerasLimp.setCellValueFactory(new PropertyValueFactory<>("er"));

      colEfectivas.setCellFactory(new Callback<TableColumn<PitcherStatsRow, Float>, TableCell<PitcherStatsRow, Float>>() {
         @Override
         public TableCell<PitcherStatsRow, Float> call(TableColumn<PitcherStatsRow, Float> column) {
            return new TableCell<PitcherStatsRow, Float>() {
               @Override
               protected void updateItem(Float value, boolean empty) {
                  super.updateItem(value, empty);
                  if (empty || value == null) {
                     setText("");
                  } else {
                     setText(String.format("%.2f", value));
                  }
               }
            };
         }
      });

      tablaStatsBateador.setItems(statsBateadorData);
      tablaStatsPitcher.setItems(statsPitcherData);
   }

   public void mostrarJugador(Jugador jugador) {
      Equipo equipo = serie.buscarEquipoDelJugador(jugador);
      aplicarEstilosEquipo(equipo);

      if (jugador instanceof Bateador) {
         mostrarBateador((Bateador) jugador);
      } else if (jugador instanceof Pitcher) {
         mostrarPitcher((Pitcher) jugador);
      }
   }

   private void mostrarBateador(Bateador bateador) {
      contenedorBateador.setVisible(true);
      contenedorPitcher.setVisible(false);

      Equipo equipoBateador = serie.buscarEquipoDelJugador(bateador);

      nombreBateador.setText(bateador.getNombre());
      infoBateador.setText(String.format("%s | #%d | %s",
            equipoBateador.getNombre(),
            bateador.getNumJugador(),
            bateador.getPosicion()));

      fechaNacBateador.setText(" " + formatFecha(bateador.getfNacimiento()));
      edadBateador.setText(String.format("%d años", bateador.getEdad()));
      alturaBateador.setText(String.format("%.2f m", bateador.getAltura()));

      EstadisticasBateador stats = bateador.getStats();
      avgBateador.setText(String.format("%.2f", stats.getPromedioBateo()));
      carrerasBateador.setText(String.valueOf(stats.getCarreras()));
      homeRunsBateador.setText(String.valueOf(stats.getHomeRuns()));

      fotoBateador.setImage(bateador.getfoto());
      logoEquipo.setImage(equipoBateador.getLogo());

      cargarStatsBateador(stats);
   }

   private void mostrarPitcher(Pitcher pitcher) {
      contenedorBateador.setVisible(false);
      contenedorPitcher.setVisible(true);

      nombrePitcher.setText(pitcher.getNombre());
      Equipo equipoPitcher = serie.buscarEquipoDelJugador(pitcher);
      infoPitcher.setText(String.format("%s | #%d | %s",
            equipoPitcher.getNombre(),
            pitcher.getNumJugador(),
            pitcher.getTipoDeLanzador()));

      fechaNacimPit.setText(" " + formatFecha(pitcher.getfNacimiento()));
      edadPitcher.setText(String.format("%d", pitcher.getEdad()));
      alturaPitcher.setText(String.format("%.2f m", pitcher.getAltura()));

      EstadisticasPitcher stats = pitcher.getStats();
      labelEraPit.setText(String.format("%.2f", stats.calcularERA()));
      labelPonchesPit.setText(String.valueOf(stats.getPonchesLanzados()));
      labelCarrerasLimpias.setText(String.valueOf(stats.getCarrerasLimpiasPermitidas()));

      fotoPitcher.setImage(pitcher.getfoto());
      logoEquipoP.setImage(equipoPitcher.getLogo());

      cargarStatsPitcher(stats);
   }

   private void cargarStatsBateador(EstadisticasBateador stats) {
      statsBateadorData.clear();
      statsBateadorData.add(new BateadorStatsRow(
            stats.getJuegosJugados(),
            stats.getHits(),
            stats.getHomeRuns(),
            stats.getCarreras(),
            stats.getPromedioBateo()));
   }

   private void cargarStatsPitcher(EstadisticasPitcher stats) {
      statsPitcherData.clear();
      statsPitcherData.add(new PitcherStatsRow(
            stats.getJuegosJugados(),
            stats.getEntradasLanzadas(),
            stats.getPonchesLanzados(),
            stats.getCarrerasLimpiasPermitidas(),
            stats.getEfectivas()));
   }

   private String formatFecha(LocalDate fecha) {
      return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
   }


   private void aplicarEstilosEquipo(Equipo equipo) {
      if (equipo == null) return;

      try {
         String colorStr = equipo.getColorPrimario().toString();

         if (colorStr.startsWith("0x") || colorStr.startsWith("0X")) {
            colorStr = colorStr.substring(2);
         }

         while (colorStr.length() < 8) {
            colorStr = "0" + colorStr;
         }

         String colorHex = "#" + colorStr;

         if (colorHex.startsWith("#FF")) {
            colorHex = "#" + colorHex.substring(3);
         }

         String estilo = "-fx-background-color: " + colorHex;

         paneBateador.setStyle(estilo);
         panePitcher.setStyle(estilo);
      } catch (Exception e) {
         System.err.println("Error al aplicar color: " + e.getMessage());
         paneBateador.setStyle("-fx-background-color: #670000");
         panePitcher.setStyle("-fx-background-color: #670000");
      }
   }

   /*
   private void cargarLogoEquipo(Equipo equipo) {
      try {
         String nombreLogo = equipo.getNombre();
         Image logo = new Image(
               getClass().getResourceAsStream("/logos/" + nombreLogo + ".png"),
               100, 100, true, true
         );

         logoEquipo.setImage(logo);
         //logoEquipoP.setImage(logo);

      } catch (Exception e) {
         //Image logoDefault = new Image("/logos/default.png");
      }
   }

   private void cargarFotoJugador(Jugador jugador) {
      Image foto = obtenerImagenJugador(jugador);

      if (jugador instanceof Bateador) {
         fotoBateador.setImage(foto);
      } else {
         fotoPitcher.setImage(foto);
      }
   }

   private Image obtenerImagenJugador(Jugador jugador) {
      try {
         if (jugador.getImagenRoute() != null) {
            File archivo = new File(jugador.getImagenRoute());
            if (archivo.exists()) {
               return new Image(archivo.toURI().toString(), 150, 150, true, true);
            }
         }

         // 2. Intento: Buscar en recursos por nombre
         String nombreArchivo = "jugador_" + jugador.getNombre() + ".png";
         InputStream is = getClass().getResourceAsStream("/fotos/" + nombreArchivo);
         if (is != null) {
            return new Image(is, 150, 150, true, true);
         }

      } catch (Exception e) {
         System.err.println("Error cargando foto: " + e.getMessage());
      }
      return null;
   }
    */

   // Clases internas para las filas de la tabla
   public static class BateadorStatsRow {
      private final int juegos;
      private final int hits;
      private final int hr;
      private final int rbi;
      private final float avg;

      public BateadorStatsRow(int juegos, int hits, int hr, int rbi, float avg) {
         this.juegos = juegos;
         this.hits = hits;
         this.hr = hr;
         this.rbi = rbi;
         this.avg = avg;
      }

      public int getJuegos() { return juegos; }
      public int getHits() { return hits; }
      public int getHr() { return hr; }
      public int getRbi() { return rbi; }
      public float getAvg() { return avg; }
   }

   public static class PitcherStatsRow {
      private final int juegos;
      private final int ip;
      private final int ponches;
      private final int er;
      private final float era;

      public PitcherStatsRow(int juegos, int ip, int ponches, int er, float era) {
         this.juegos = juegos;
         this.ip = ip;
         this.ponches = ponches;
         this.er = er;
         this.era = era;
      }

      public int getJuegos() { return juegos; }
      public int getIp() { return ip; }
      public int getPonches() { return ponches; }
      public int getEr() { return er; }
      public float getEra() { return era; }
   }
}