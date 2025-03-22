package logic;

import java.util.ArrayList;

public class SerieMundial {

    private static SerieMundial instance = null;
    ArrayList<Partido> partidos;
    ArrayList<Jugador> jugadores;

    private SerieMundial() {
        partidos = new ArrayList<>();
        jugadores = new ArrayList<>();
    }

    public static SerieMundial getInstance() {
        if (instance == null) {
            return new SerieMundial();
        }
        return instance;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public void addPartido(Partido p) {
        partidos.add(p);
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }


}
