package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.Partido;
import logic.Temporada;

public class ControllerSimulador {
   @FXML private Label fechaLabel, estadioLabel;
   @FXML private Label equipoVisitanteLabel, equipoLocalLabel;
   @FXML private Label carrerasVisitanteLabel, hitsVisitanteLabel, erroresVisitanteLabel;
   @FXML private Label inningLabel, bolasStrikesLabel, outsLabel;
   @FXML private Label carrerasLocalLabel, hitsLocalLabel, erroresLocalLabel;
   @FXML private Label comentariosArea;
   @FXML private Button btnIniciar, btnPausar;
   @FXML private Rectangle primeraBase, segundaBase, terceraBase;
   @FXML private Label equipoTabVisit, equipoTabLocal;
   @FXML private Label primerVisitante, segundoVisitante, tercerVisitante, cuartoVisitante, quintoVisitante, sextoVisitante, septimoVisitante, octavoVisitante, novenoVisitante;
   @FXML private Label primerLocal, segundoLocal, tercerLocal, cuartoLocal, quintoLocal, sextoLocal, septimoLocal, octavoLocal, novenoLocal;
   @FXML private Slider sliderVelocidad;
   @FXML private VBox vboxComentarios;
   @FXML private ScrollPane scrollComentarios;

   private int currentDisplayedInning = 0;
   private boolean currentDisplayedTopInning = true;
   private int currentCommentIndex = 0;
   private Partido partido;
   private Timeline timeline;

   public void setPartido(Partido partido) {
      this.partido = partido;
   }

   @FXML
   public void initialize() {
      configurarTimeline();
      configurarControles();
      configurarControlVelocidad();
   }

   private void configurarTimeline() {
      timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
         if (partido != null && partido.isPartidoTerminado()) {
            actualizarInterfazCompleta();
         }
      }));
      timeline.setCycleCount(Timeline.INDEFINITE);
   }

   public void setTemporada(Temporada temporada) {
      if (temporada != null && !temporada.getPartidos().isEmpty()) {
         this.partido = temporada.getPartidos().get(0);
         actualizarInterfazInicial();
      }
   }

   private void configurarControles() {
      btnIniciar.setOnAction(e -> iniciarSimulacion());
      btnPausar.setOnAction(e -> pausarSimulacion());
   }

   private void configurarControlVelocidad() {
      sliderVelocidad.valueProperty().addListener((obs, oldVal, newVal) -> {
         if (timeline != null) {
            timeline.setRate(newVal.doubleValue());  // Ajusta la velocidad
         }
      });
   }

   public void inicializarPartido(Partido partido) {
      this.partido = partido;

      // Inicializar UI
      fechaLabel.setText(partido.getFecha().toString());
      estadioLabel.setText(partido.getEstadio());
      equipoVisitanteLabel.setText(partido.getEquipoVisitante().getNombre());
      equipoLocalLabel.setText(partido.getEquipoLocal().getNombre());

      equipoTabVisit.setText(partido.getEquipoVisitante().getNombre());
      equipoTabLocal.setText(partido.getEquipoLocal().getNombre());

      resetearEstadisticas();
      configurarTimeline();
   }

   @FXML
   private void iniciarSimulacion() {
      if (partido == null || partido.isPartidoTerminado()) return;
      if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) return;

      agregarNuevoPanelInning(1, true); // Agregar el primer inning

      double velocidadInicial = 1.0;
      sliderVelocidad.setValue(velocidadInicial);

      timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
         partido.simularSiguienteJugada();
         actualizarInterfazCompleta();
         if (partido.isPartidoTerminado()) {
            timeline.stop();
         }
      }));
      timeline.setRate(velocidadInicial);
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();
   }

   @FXML
   private void pausarSimulacion() {
      if (timeline != null) timeline.pause();
   }

   @FXML
   private void reanudarSimulacion() {
      if (timeline != null) timeline.play();
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

      // Reset de comentarios
      vboxComentarios.getChildren().clear();
      currentDisplayedInning = 1;
      currentDisplayedTopInning = true;
      currentCommentIndex = 0;
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

      // Actualizar etiquetas de estadísticas
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
      actualizarTablaCarreras();

      // Cambió el inning o el lado (alta/baja)
      if (currentDisplayedInning != partido.getInningActual() ||
            currentDisplayedTopInning != partido.isEsTopInning()) {

         currentDisplayedInning = partido.getInningActual();
         currentDisplayedTopInning = partido.isEsTopInning();
         agregarNuevoPanelInning(currentDisplayedInning, currentDisplayedTopInning);
      }

      // Agregar comentarios del inning actual
      while (currentCommentIndex < partido.getResumenPartido().size()) {
         String comentario = partido.getResumenPartido().get(currentCommentIndex);
         agregarComentarioAlPanelActual(comentario);
         currentCommentIndex++;
      }

      // Auto-scroll para ver el último inning agregado
      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            scrollComentarios.setVvalue(0);
         }
      });
   }

   private void actualizarEstadoBases() {
      if (partido == null) return;
      primeraBase.setFill(partido.hayCorredorEn(1) ? Color.BLUEVIOLET : Color.WHITE);
      segundaBase.setFill(partido.hayCorredorEn(2) ? Color.BLUEVIOLET : Color.WHITE);
      terceraBase.setFill(partido.hayCorredorEn(3) ? Color.BLUEVIOLET : Color.WHITE);
   }

   private void agregarNuevoPanelInning(int inning, boolean isTop) {
      VBox inningBox = new VBox();
      inningBox.setSpacing(5);
      inningBox.setStyle("-fx-padding: 10; -fx-border-color: white; -fx-border-width: 1; -fx-background-color: black;");

      // Título del inning
      Label lblInning = new Label(String.format("Inning %d %s", inning, isTop ? "▲" : "▼"));
      lblInning.setFont(Font.font("Arial", FontWeight.BOLD, 16));
      lblInning.setTextFill(Color.WHITE);

      // VBox para comentarios (nuevos arriba)
      VBox comentariosBox = new VBox();
      comentariosBox.setSpacing(3);
      comentariosBox.setStyle("-fx-padding: 5 0 0 0;");

      // ScrollPane interno para cada inning
      ScrollPane scrollComentariosInning = new ScrollPane();
      scrollComentariosInning.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      scrollComentariosInning.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
      scrollComentariosInning.setContent(comentariosBox);
      scrollComentariosInning.setFitToWidth(true);
      scrollComentariosInning.setPrefSize(770, 120);
      scrollComentariosInning.setStyle("-fx-background: black; -fx-border-color: transparent;");
      scrollComentariosInning.setPrefHeight(150); // Altura fija para cada panel de inning

      // Agregar elementos
      inningBox.getChildren().addAll(lblInning, scrollComentariosInning);

      // Insertar al inicio del contenedor principal (innings nuevos arriba)
      vboxComentarios.getChildren().add(0, inningBox);

      // Hacer scroll al inicio para mostrar el nuevo inning
      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            scrollComentarios.setVvalue(0);
            scrollComentarios.requestLayout();
         }
      });
   }

   private void agregarComentarioAlPanelActual(String comentario) {
      if (vboxComentarios.getChildren().isEmpty()) return;

      // Obtener el último inning agregado
      VBox ultimoInningBox = (VBox) vboxComentarios.getChildren().get(0);
      ScrollPane scrollPane = (ScrollPane) ultimoInningBox.getChildren().get(1);
      VBox comentariosBox = (VBox) scrollPane.getContent();

      // Crear Label para el comentario
      Label lblComentario = new Label("• " + comentario);
      lblComentario.setWrapText(true);
      lblComentario.setMaxWidth(750); // Un poco menos que el ancho total
      lblComentario.setTextFill(Color.WHITE);
      lblComentario.setFont(Font.font("Arial", 14));
      lblComentario.setPadding(new Insets(0, 0, 3, 5));

      // Insertar el comentario al inicio del VBox (nuevos arriba)
      comentariosBox.getChildren().add(0, lblComentario);

      // Auto-scroll para el panel de comentarios del inning
      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            scrollPane.setVvalue(0); // Mostrar arriba (lo más nuevo)
            scrollPane.requestLayout();
         }
      });
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
