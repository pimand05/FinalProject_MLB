package logic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pitcher extends Jugador {
    // private float efectivas; Se pasa a Estadisticas Pitcher
    private String tipoDeLanzador; //Abridor o relevista
    private boolean esZurdo;
    private ArrayList<String> listaPicheos;
    private EstadisticasPitcher stats;
    private int turnos = 0;

    public Pitcher(String nombre, LocalDate fNacimiento, float altura, int numJugador, float efectivas, String tipoDeLanzador, boolean esZurdo, ArrayList<String> listaPicheos, EstadisticasPitcher stats, int turnos) {
        super(nombre, fNacimiento, altura, numJugador);
        //this.efectivas = efectivas;
        this.tipoDeLanzador = tipoDeLanzador;
        this.esZurdo = esZurdo;
        this.listaPicheos = listaPicheos;
        this.stats = new EstadisticasPitcher(0,0,0, 0, 0);
        this.turnos = turnos;
    }

//    public float getEfectivas() {
//        return efectivas;
//    }

    public String getTipoDeLanzador() {
        return tipoDeLanzador;
    }

    public boolean isEsZurdo() {
        return esZurdo;
    }

    public ArrayList<String> getListaPicheos() {
        return listaPicheos;
    }

    public EstadisticasPitcher getStats() {
        return stats;
    }

//    public void setEfectivas(float efectivas) {
//        this.efectivas = efectivas;
//    }

    public void setTipoDeLanzador(String tipoDeLanzador) {
        this.tipoDeLanzador = tipoDeLanzador;
    }

    public void setEsZurdo(boolean esZurdo) {
        this.esZurdo = esZurdo;
    }

    public void setListaPicheos(ArrayList<String> listaPicheos) {
        this.listaPicheos = listaPicheos;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    @Override
    public void actualizarEstadisticas(Partido partido) {
    }

    public void incrementarTurnos() {
        turnos++;
    }
}
