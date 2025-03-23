package logic;

import java.util.ArrayList;

public class Equipo {
    private String nombre;
    private String estadio;
    private Boolean calificado;
    private int juegosGanados;
    private int juegosPerdidos;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Partido> partidosJugados;

    //Constructor
    public Equipo(String nombre, String estadio, Boolean calificado, int juegosGanados, int juegosPerdidos) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.calificado = calificado;
        this.juegosGanados = juegosGanados;
        this.juegosPerdidos = juegosPerdidos;
    }

    //Getters y Setters
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

    public ArrayList<Partido> getPartidosJugados() {
        return partidosJugados;
    }

    //metodos

    public void setJuegosjugados(ArrayList<Partido> partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void removeJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public void addPartido(Partido partido) {
        partidosJugados.add(partido);
    }
}
