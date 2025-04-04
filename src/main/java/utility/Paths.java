package utility;

import logic.Jugador;

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


    public static final String INSTANCIA = "src/main/Resource/Text/instancia.json";


    //Equipo
    public static final String FOLDEREQUIPO = "src/main/Resource/Picture/";
    public static final String FOLDERJUGADOR = "src/main/Resource/Picture/";
    public static final String EQUIPODF = "src/main/Resource/Picture/DefaultIcon.png";

    public static String getJugadoresFolderForEquipo(String nombreEquipo, String nombreJugador) {
        return "src/main/Resource/Picture/" + nombreEquipo.trim().replaceAll("\s+", "_") + "/Jugadores/" + nombreJugador.trim().replaceAll("\s+", "_");
    }

    //MainApp
    public static final String ICONMAIN = "/picture/BaseballIcon.png";
    public static final String VIDEOINTRO = "/video/VideoIntro.mp4";
    public static final String VIDEOINTRO2 = "/video/VideoIntro2.mp4";


}
