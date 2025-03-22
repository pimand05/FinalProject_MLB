package logic;

public class Equipo {
    private String nombre;
    private String estadio;
    private Boolean calificado;
    private int juegosGanados;
    private int juegosPerdidos;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Juego> juegosjugados;

    public Equipo(String nombre, String estadio, Boolean calificado, int juegosGanados, int juegosPerdidos, ArrayList<Jugador> jugadores, ArrayList<Juego> juegosjugados) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.calificado = calificado;
        this.juegosGanados = juegosGanados;
        this.juegosPerdidos = juegosPerdidos;
        this.jugadores = jugadores;
        this.juegosjugados = juegosjugados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public Boolean getCalificado() {
        return calificado;
    }

    public void setCalificado(Boolean calificado) {
        this.calificado = calificado;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public void setJuegosPerdidos(int juegosPerdidos) {
        this.juegosPerdidos = juegosPerdidos;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public ArrayList<Juego> getJuegosjugados() {
        return juegosjugados;
    }

    public void setJuegosjugados(ArrayList<Juego> juegosjugados) {
        this.juegosjugados = juegosjugados;
    }


}
