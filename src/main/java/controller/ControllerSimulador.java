package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Partido;
import logic.Temporada;

import java.util.Arrays;

public class ControllerSimulador {
   @FXML private Label fechaLabel, estadioLabel;
   @FXML private Label equipoVisitanteLabel, equipoLocalLabel;
   @FXML private Label inningLabel, bolasStrikesLabel, outsLabel;
   @FXML private Label carrerasVisitanteLabel, hitsVisitanteLabel, erroresVisitanteLabel;
   @FXML private Label carrerasLocalLabel, hitsLocalLabel, erroresLocalLabel;
   @FXML private TextArea comentariosArea;
   @FXML private Button btnIniciar, btnPausar;
   @FXML private Rectangle primeraBase, segundaBase, terceraBase;
   @FXML private Label equipoTabVisit, equipoTabLocal;
   @FXML private Label primerVisitante, segundoVisitante, tercerVisitante, cuartoVisitante, quintoVisitante, sextoVisitante, septimoVisitante, octavoVisitante, novenoVisitante;
   @FXML private Label primerLocal, segundoLocal, tercerLocal, cuartoLocal, quintoLocal, sextoLocal, septimoLocal, octavoLocal, novenoLocal;

   private Partido partido;
   private Timeline timeline;
   private Temporada temporada;
   private int currentInning = 0;
   private int currentCommentIndex = 0;

   @FXML
   public void initialize() {
      configurarTimeline();
      configurarControles();
   }

   public void setTemporada(Temporada temporada) {
      this.temporada = temporada;
      if (temporada != null && !temporada.getPartidos().isEmpty()) {
         this.partido = temporada.getPartidos().get(0);
         actualizarInterfazInicial();
      }
   }

   private void configurarTimeline() {
      timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
         if (partido != null && partido.isPartidoTerminado()) {
            actualizarInterfazCompleta();
         }
      }));
      timeline.setCycleCount(Timeline.INDEFINITE);
   }

   private void configurarControles() {
      btnIniciar.setOnAction(e -> iniciarSimulacion());
      btnPausar.setOnAction(e -> pausarSimulacion());
      //Falta el boton reanudar y el slider
   }

   @FXML
   private void iniciarSimulacion() {
      if (partido == null || partido.isPartidoTerminado()) return;
      if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) return;
      comentariosArea.appendText("  Juego entre " + partido.getEquipoLocal().getNombre() + " y " + partido.getEquipoVisitante().getNombre() + " iniciado\n\n");

      timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
         partido.simularSiguienteJugada();
         actualizarInterfazCompleta();
         if (partido.isPartidoTerminado()) {
            timeline.stop();
         }
      }));
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
   }

   @FXML
   private void pausarSimulacion() {
      if (timeline != null) {
         timeline.pause();
      }
   }

   @FXML
   private void reanudarSimulacion() {
      if (timeline != null) {
         timeline.play();
      }
   }

   private void actualizarInterfazInicial() {
      if (partido == null) return;

      fechaLabel.setText(partido.getFecha().toString());
      estadioLabel.setText(partido.getEstadio());
      equipoVisitanteLabel.setText(partido.getEquipoVisitante().getNombre());
      equipoLocalLabel.setText(partido.getEquipoLocal().getNombre());
      equipoTabVisit.setText(partido.getEquipoVisitante().getNombre());
      equipoTabLocal.setText(partido.getEquipoLocal().getNombre());
      resetearEstadisticas();
   }

   private void resetearEstadisticas() {
      carrerasVisitanteLabel.setText("0");
      hitsVisitanteLabel.setText("0");
      erroresVisitanteLabel.setText("0");
      carrerasLocalLabel.setText("0");
      hitsLocalLabel.setText("0");
      erroresLocalLabel.setText("0");
      inningLabel.setText("Inning 1 ▲");
      bolasStrikesLabel.setText("0 - 0");
      outsLabel.setText("0 OUT");
      actualizarEstadoBases();
   }

   private void actualizarInterfazCompleta() {
      if (partido == null) return;

      carrerasVisitanteLabel.setText(String.valueOf(partido.getCarrerasVisitante()));
      hitsVisitanteLabel.setText(String.valueOf(partido.getHitsVisitante()));
      erroresVisitanteLabel.setText(String.valueOf(partido.getErroresVisitante()));

      carrerasLocalLabel.setText(String.valueOf(partido.getCarrerasLocal()));
      hitsLocalLabel.setText(String.valueOf(partido.getHitsLocal()));
      erroresLocalLabel.setText(String.valueOf(partido.getErroresLocal()));

      inningLabel.setText(String.format("Inning %d %s", partido.getInningActual(), partido.isEsTopInning() ? "▲" : "▼"));
      bolasStrikesLabel.setText(partido.getBolas() + " - " + partido.getStrikes());
      outsLabel.setText(partido.getOuts() + " OUT");

      actualizarEstadoBases();
      mostrarSiguienteComentario();
      actualizarTablaCarreras();
   }

   private void actualizarEstadoBases() {
      if (partido == null) return;
      primeraBase.setFill(partido.hayCorredorEn(1) ? Color.BLUEVIOLET : Color.WHITE);
      segundaBase.setFill(partido.hayCorredorEn(2) ? Color.BLUEVIOLET : Color.WHITE);
      terceraBase.setFill(partido.hayCorredorEn(3) ? Color.BLUEVIOLET : Color.WHITE);
   }

   private void mostrarSiguienteComentario() {
      if (partido == null || partido.getResumenPartido() == null) return;

      while (currentCommentIndex < partido.getResumenPartido().size()) {
         String comentario = partido.getResumenPartido().get(currentCommentIndex);
         if (currentInning != partido.getInningActual()){
            currentInning = partido.getInningActual();
            comentariosArea.appendText("\n  Inning " + currentInning + " " + (partido.isEsTopInning() ? "▲" : "▼") + "\n");
         }
         comentariosArea.appendText(comentario + "\n");
         currentCommentIndex++;
      }
   }

   private void actualizarTablaCarreras() {
      if (partido == null) return;

      Label[] columnasVisitante = {primerVisitante, segundoVisitante, tercerVisitante, cuartoVisitante, quintoVisitante, sextoVisitante, septimoVisitante, octavoVisitante, novenoVisitante};
      Label[] columnasLocal = {primerLocal, segundoLocal, tercerLocal, cuartoLocal, quintoLocal, sextoLocal, septimoLocal, octavoLocal, novenoLocal};

      int[] carrerasVisitante = partido.getCarrerasPorInningVisitante();
      int[] carrerasLocal = partido.getCarrerasPorInningLocal();

      for (int i = 0; i < columnasVisitante.length; i++) {
         columnasVisitante[i].setText(i < carrerasVisitante.length ? String.valueOf(carrerasVisitante[i]) : "0");
         columnasLocal[i].setText(i < carrerasLocal.length ? String.valueOf(carrerasLocal[i]) : "0");
      }
   }
}
