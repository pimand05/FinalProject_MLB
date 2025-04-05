package utility;

public class Paths {

    //Views
    public static final String MAIN = "/fileFXML/mainView.fxml";
    public static final String POSICIONES = "/fileFXML/posiciones.fxml";
    public static final String CALENDARIO = "/fileFXML/Calendario.fxml";
    public static final String SIMULADOR = "/fileFXML/Simulador.fxml";
    public static final String RGEQUIPO = "/fileFXML/regEquipo.fxml";
    public static final String TABLAPOSICIONES = "/fileFXML/TablaPosiciones.fxml";
    public static final String REGJUGADOR = "/fileFXML/RegJugador.fxml";
    public static final String STATSJUGADORES = "/fileFXML/StatsJugadores.fxml";
    public static final String JUGADORES = "/fileFXML/Jugadores.fxml";
    public static final String EQUIPOS = "/fileFXML/Equipos.fxml";


    public static final String INSTANCIA = "src/main/resource/text/instancia.json";

    public static final String RUTA = "src/main/resource/";


    //Equipo
    public static final String FOLDEREQUIPO = "src/main/resource/picture/";
    public static final String FOLDERJUGADOR = "src/main/resource/picture/";
    public static final String EQUIPODF = "src/main/resource/picture/icons/DefaultIcon.png";

    public static String getJugadoresFolderForEquipo(String nombreEquipo, String nombreJugador) {
        return "src/main/Resource/Picture/" + nombreEquipo.trim().replaceAll("\s+", "_") + "/Jugadores/" + nombreJugador.trim().replaceAll("\s+", "_");
    }

    //MainApp
    public static final String ICONMAIN = "/picture/icons/BaseballIcon.png";
    public static final String VIDEOINTRO = "/video/VideoIntro.mp4";
    public static final String VIDEOINTRO2 = "/video/VideoIntro2.mp4";


}
