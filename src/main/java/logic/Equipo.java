package logic;

import java.util.ArrayList;
import java.util.HashMap;

public class Equipo {
    private String nombre;
    private String estadio;
    private Boolean calificado;
    private int juegosGanados;
    private int juegosPerdidos;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Partido> partidosJugados;
    HashMap<Integer, Bateador> lineup;
    HashMap<Integer, Pitcher> relevistas;

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

    public Jugador getJugador(int turno) {
        return lineup.get(turno);
    }

    public void setLineup(HashMap<Integer, Bateador> lineup) {
        this.lineup = lineup;
    }

    public void setPartidosJugados(ArrayList<Partido> partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public HashMap<Integer, Bateador> getLineup() {
        return lineup;
    }

    public void setRelevistas(HashMap<Integer, Pitcher> relevistas) {
        this.relevistas = relevistas;
    }

    public void settingLineup() {
        int i = 1;
        for (Jugador jugador : jugadores) {
            if (i > 9) break;
            if (jugador instanceof Bateador bateador && !(bateador.isLesionado())) {
                lineup.put(i, bateador);
            }
            i++;

        }
        setLineup(lineup);
    }

    public Pitcher getPitcher() {
        Pitcher pitcher = null;
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Pitcher) {
                pitcher = (Pitcher) jugador;
                break;
            }
        }
        return pitcher;
    }

    public void getRelevistas() {
        int i = 1;
        for (Jugador jugador : jugadores) {
            if (i > 5) break;
            if (jugador instanceof Pitcher pitcher) {
                relevistas.put(i, pitcher);
            }
            i++;

        }
        setRelevistas(relevistas);
    }
}
