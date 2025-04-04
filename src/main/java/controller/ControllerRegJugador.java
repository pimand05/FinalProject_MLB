package controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.*;
import utility.GuardarImagen;
import utility.Paths;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerRegJugador {

   @FXML private TextField txtNombre;
   @FXML private DatePicker calFecNacim;
   @FXML private Spinner<Integer> spnNumero;
   @FXML private TextField txtAltura;
   @FXML private RadioButton rbBateador;
   @FXML private RadioButton rbPitcher;
   @FXML private Pane infoBateador;
   @FXML private Pane infoPitcher;
   @FXML private ComboBox<String> cmbPosicion;
   @FXML private ComboBox<String> cmbTipoLanzador;
   @FXML private RadioButton rdbIzquierda;
   @FXML private RadioButton rdbDerecha;
   @FXML private ListView<String> listViewTiposPicheos;
   @FXML private Button btnRegistrar;
   @FXML private Button btnCancelar;
   @FXML private Button btnSelecEquipo;
   @FXML private Label lblFoto;
   @FXML private StackPane stackFoto;

   // Manejo de Imagen
   private String stackPane;
   private File imagenTemporal = null;

   //Elementos Tabla
   @FXML private TableView<rowEquipo> tablaEquipos;
   @FXML private TableColumn<rowEquipo, String> colEquipo;
   @FXML private TableColumn<rowEquipo, Image> colLogo;

   private ToggleGroup posicionGroup;
   private ToggleGroup manoGroup;

   SerieMundial serie = SerieMundial.getInstance();

   @FXML
   void handleDragOver(DragEvent event) {
      Dragboard db = event.getDragboard();
      if (db.hasFiles()) {
         event.acceptTransferModes(TransferMode.COPY);
      }
      event.consume();
   }

   @FXML
   void handleDragDropped(DragEvent event) {
      Dragboard db = event.getDragboard();
      boolean success = false;
      if (db.hasFiles()) {
         imagenTemporal = db.getFiles().getFirst(); // Obtenemos el primer archivo (se puede extender para múltiples archivos)
         mostrarImagen(imagenTemporal);
         success = true;
      }
      event.setDropCompleted(success);
      event.consume();
   }

   private void mostrarImagen(File file) {
      Image image = new Image(file.toURI().toString());
      showPane(image);
   }

   private void showPane(Image image) {
      String imageUrl = image.getUrl();
      stackFoto.setStyle(
            "-fx-background-image: url('" + imageUrl + "');" +
                  "-fx-background-size: contain;" +
                  "-fx-background-position: center center;" +
                  "-fx-background-color: white;"  +
                  "-fx-background-repeat: no-repeat;"
      );
      lblFoto.setVisible(false);
   }


   @FXML
   public void initialize() {
      configurarColumnas();

      // Configurar grupos de radio buttons
      posicionGroup = new ToggleGroup();
      rbBateador.setToggleGroup(posicionGroup);
      rbPitcher.setToggleGroup(posicionGroup);

      manoGroup = new ToggleGroup();
      rdbIzquierda.setToggleGroup(manoGroup);
      rdbDerecha.setToggleGroup(manoGroup);

      // Configurar valores de los ChoiceBox
      cmbPosicion.getItems().addAll("C", "1B", "2B", "3B", "SS", "LF", "CF", "RF");
      cmbTipoLanzador.getItems().addAll("Abridor", "Relevista", "Cerrador");
      listViewTiposPicheos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

      // Configurar Spinner para número de jugador (1-99)
      SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
      spnNumero.setValueFactory(valueFactory);

      // Mostrar/ocultar paneles según selección
      rbBateador.selectedProperty().addListener((obs, oldVal, newVal) -> {
         infoBateador.setVisible(newVal);
         infoPitcher.setVisible(!newVal);
      });

      rbPitcher.selectedProperty().addListener((obs, oldVal, newVal) -> {
         infoPitcher.setVisible(newVal);
         infoBateador.setVisible(!newVal);
      });

      // Seleccionar bateador por defecto
      rbBateador.setSelected(true);

      // Configurar botones
      btnRegistrar.setOnAction(e -> registrarJugador());
      btnCancelar.setOnAction(e -> cancelarRegistro());
      btnSelecEquipo.setOnAction(e -> selecEquipo());
   }

   @FXML
   public Equipo selecEquipo() {
      rowEquipo filaSeleccionada = tablaEquipos.getSelectionModel().getSelectedItem();

      if (filaSeleccionada == null) {
         mostrarAlerta("Error", "Ningún equipo seleccionado");
      }

      for (Equipo equipo : serie.getEquipos()) {
         if (equipo.getNombre().equals(filaSeleccionada.getNombre())) {
            return equipo;
         }
      }

      mostrarAlerta("Error", "Equipo no encontrado en los registros");
      return null;
   }

   private void registrarJugador() {
      try {
         // Validar campos obligatorios
         if (txtNombre.getText().isEmpty() || calFecNacim.getValue() == null) {
            mostrarAlerta("Error", "Nombre y fecha de nacimiento son obligatorios");
            return;
         }

         // Validar selección de equipo
         Equipo equipoJugador = selecEquipo();
         if (equipoJugador == null) {
            return;
         }

         String ruta = Paths.FOLDERJUGADOR;

         // Crear jugador según tipo seleccionado
         Jugador jugador;
         String nombre = txtNombre.getText();
         float altura = Float.parseFloat(txtAltura.getText());
         LocalDate fechaNacimiento = calFecNacim.getValue();
         int numero = spnNumero.getValue();
         ruta = GuardarImagen.guardar(imagenTemporal, "foto_"+nombre.trim().replaceAll("\\s+", "_"),Paths.FOLDERJUGADOR+nombre.trim().replaceAll("\\s+", "_"));

         if (rbBateador.isSelected()) {
            String posicion = cmbPosicion.getValue();
            if (posicion == null) {
               mostrarAlerta("Error", "Seleccione una posición para el bateador");
               return;
            }
            jugador = new Bateador(nombre, fechaNacimiento,  altura, numero, ruta, posicion);
         } else {
            String tipoLanzador = cmbTipoLanzador.getValue();
            String manoDominante = rdbIzquierda.isSelected() ? "Izquierda" : "Derecha";
            ArrayList<String> tiposSeleccionados = new ArrayList<>(
                  listViewTiposPicheos.getSelectionModel().getSelectedItems()
            );

            if (tipoLanzador == null || tiposSeleccionados.isEmpty()) {
               mostrarAlerta("Error", "Complete todos los campos del pitcher");
               return;
            }

            boolean esZurdo = rdbIzquierda.isSelected();
            jugador = new Pitcher(nombre, fechaNacimiento, altura, numero, ruta,
                  tipoLanzador, esZurdo, tiposSeleccionados);
         }

         // Asignar jugador al equipo (tanto bateadores como pitchers)
         equipoJugador.addJugador(jugador);


         limpiarFormulario();
         mostrarAlerta("Éxito", "Jugador registrado correctamente");

      } catch (NumberFormatException e) {
         mostrarAlerta("Error", "Altura debe ser un número válido");
      } catch (Exception e) {
         mostrarAlerta("Error", "Ocurrió un error al registrar el jugador: " + e.getMessage());
      }
   }

   private void cancelarRegistro() {
      Stage stage = (Stage) btnCancelar.getScene().getWindow();
      stage.close();
   }

   
   private void limpiarFormulario() {
      txtNombre.clear();
      calFecNacim.setValue(null);
      spnNumero.getValueFactory().setValue(1);
      rbBateador.setSelected(true);
      cmbPosicion.setValue(null);
      cmbTipoLanzador.setValue(null);
      listViewTiposPicheos.getSelectionModel().clearSelection();
      rdbIzquierda.setSelected(false);
      rdbDerecha.setSelected(false);
      restorePane();
   }

   private void restorePane() {
      stackFoto.setStyle(stackPane);
      lblFoto.setVisible(true);
   }

   private void mostrarAlerta(String titulo, String mensaje) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(titulo);
      alert.setHeaderText(null);
      alert.setContentText(mensaje);
      alert.showAndWait();
   }

   private void configurarColumnas() {
      // Configuración básica de la columna
      colEquipo.setCellValueFactory(new PropertyValueFactory<>("nombre"));

      // Opcional: Configuración adicional de la columna
      colEquipo.setStyle("-fx-alignment: CENTER;");
      colEquipo.setPrefWidth(200);

      cargarEquipos();
   }

   private void cargarEquipos() {
      // Crear lista observable para los equipos
      ObservableList<rowEquipo> listaEquipos = FXCollections.observableArrayList();

      // Obtener la lista original de equipos
      List<Equipo> equiposOriginales = serie.getEquipos();

      // Convertir cada Equipo a rowEquipo usando un bucle tradicional
      for (Equipo equipo : equiposOriginales) {
         rowEquipo fila = new rowEquipo(equipo.getNombre());
         listaEquipos.add(fila);
      }

      // Asignar la lista convertida a la tabla
      tablaEquipos.setItems(listaEquipos);
   }

   // Clase interna para representar filas de la tabla
   public static class rowEquipo {
      private final String nombre;

      public rowEquipo(String nombre) {
         this.nombre = nombre;
      }

      public String getNombre() {
         return nombre;
      }

      @Override
      public String toString() {
         return nombre;
      }
   }

}
