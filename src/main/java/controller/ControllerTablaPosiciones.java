package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import logic.Equipo;
import java.util.Comparator;
import java.util.List;

public class ControllerTablaPosiciones {

   @FXML private TableView<FilaTablaPosiciones> tablaPosiciones;
   @FXML private TableColumn<FilaTablaPosiciones, String> colEquipo;
   @FXML private TableColumn<FilaTablaPosiciones, Integer> colGanados;
   @FXML private TableColumn<FilaTablaPosiciones, Integer> colPerdidos;
   @FXML private TableColumn<FilaTablaPosiciones, String> colDiferencia;

   @FXML
   public void initialize() {
      configurarColumnas();
   }

   private void configurarColumnas() {
      // Usamos PropertyValueFactory con los nombres de los getters
      colEquipo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("equipo"));
      colGanados.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ganados"));
      colPerdidos.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("perdidos"));
      colDiferencia.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("diferencia"));

      // Centrar contenido en columnas numéricas
      colGanados.setStyle("-fx-alignment: CENTER;");
      colPerdidos.setStyle("-fx-alignment: CENTER;");
      colDiferencia.setStyle("-fx-alignment: CENTER;");
   }

   public void actualizarTabla(List<Equipo> equipos) {
      if (equipos == null || equipos.isEmpty()) return;

      // Ordenar equipos por partidos ganados (descendente)
      equipos.sort(Comparator.comparingInt(Equipo::getJuegosGanados).reversed());

      // Obtener estadísticas del líder
      int ganadosLider = equipos.get(0).getJuegosGanados();
      int perdidosLider = equipos.get(0).getJuegosPerdidos();

      ObservableList<FilaTablaPosiciones> datos = FXCollections.observableArrayList();

      for (Equipo equipo : equipos) {
         int diferencia = calcularDiferencia(ganadosLider, perdidosLider, equipo);
         datos.add(new FilaTablaPosiciones(
               equipo.getNombre(),
               equipo.getJuegosGanados(),
               equipo.getJuegosPerdidos(),
               formatearDiferencia(diferencia)
         ));
      }

      tablaPosiciones.setItems(datos);
   }

   private int calcularDiferencia(int ganadosLider, int perdidosLider, Equipo equipo) {
      return (ganadosLider - equipo.getJuegosGanados()) +
            (equipo.getJuegosPerdidos() - perdidosLider);
   }

   private String formatearDiferencia(int diferencia) {
      return diferencia == 0 ? "-" : String.format("%.1f", diferencia / 2.0);
   }

   // Clase interna para representar filas de la tabla
   public static class FilaTablaPosiciones {
      private final String equipo;
      private final int ganados;
      private final int perdidos;
      private final String diferencia;

      public FilaTablaPosiciones(String equipo, int ganados, int perdidos, String diferencia) {
         this.equipo = equipo;
         this.ganados = ganados;
         this.perdidos = perdidos;
         this.diferencia = diferencia;
      }

      // Getters
      public String getEquipo() { return equipo; }
      public int getGanados() { return ganados; }
      public int getPerdidos() { return perdidos; }
      public String getDiferencia() { return diferencia; }
   }
}