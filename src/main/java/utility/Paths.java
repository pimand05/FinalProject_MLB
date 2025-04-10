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
    public static final String EQUIPOS2 = "/fileFXML/Equipos2.fxml";
    public static final String INFOEQUIPO = "/fileFXML/InfoEquipo.fxml";
    public static final String DASHBOARD = "/fileFXML/dashboard.fxml";
    public static final String ABOUT = "/fileFXML/about.fxml";
    public static final String CONFIGURACION = "/fileFXML/Configuracion.fxml";
    public static final String LOGIN = "/fileFXML/Login.fxml";


    public static final String INSTANCIA = "src/main/resource/text/instancia.json";

    public static final String RUTA = "src/main/resource/";


    //Equipo
    public static final String FOLDEREQUIPO = "src/main/resource/picture/";
    public static final String FOLDERJUGADOR = "src/main/resource/picture/";
    public static final String EQUIPODF = "/picture/icons/DefaultIcon.png";
    public static final String JUGADORDF = "/picture/icons/DefaultFoto.png";
    //public static final String EDITAREQUIPO = "/fileFXML/editarEquipo.fxml";
    public static final String EDITAREQUIPO = "/fileFXML/regEquipo.fxml";


    public static String getJugadoresFolderForEquipo(String nombreEquipo, String nombreJugador) {
        return "src/main/resource/picture/" + nombreEquipo.trim().replaceAll("\s+", "_") + "/jugadores/" + nombreJugador.trim().replaceAll("\s+", "_");
    }

    //MainApp
    public static final String ICONMAIN = "/picture/icons/BaseballIcon.png";
    public static final String ICONEQUIPO = "/picture/icons/TeamIcon.png";
    public static final String ICONABOUT = "/picture/icons/InformationIcon.png";
    public static final String ICONABOUTWH = "/picture/icons/IconInformationWhite.png";
    public static final String TROFEOICON = "/picture/icons/TrofeoIcon.png";
    public static final String VIDEOINTRO = "/video/VideoIntro.mp4";
    public static final String VIDEOINTRO2 = "/video/VideoIntro2.mp4";


    public static final String SEBAFOTO = "/picture/icons/pictureAbout/SebastianAbout.jpg";
    public static final String MANUELFOTO = "/picture/icons/pictureAbout/ManuelFoto.JPG";
    public static final String MARIAFOTO = "/picture/icons/pictureAbout/MariaFoto.JPG";

}
