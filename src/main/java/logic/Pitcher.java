package logic;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Pitcher extends Jugador  implements Serializable {
    private String tipoDeLanzador; //Abridor o relevista
    private static final long serialVersionUID = 1L;
    private boolean esZurdo;
    private ArrayList<String> listaPicheos;
    private EstadisticasPitcher stats;
    private int turnos = 0;

    public Pitcher(String nombre, LocalDate fNacimiento, float altura, int numJugador, String imagenRoute, String tipoDeLanzador, boolean esZurdo, ArrayList<String> listaPicheos) {
        super(nombre, fNacimiento, altura, numJugador, imagenRoute);
        this.tipoDeLanzador = tipoDeLanzador;
        this.esZurdo = esZurdo;
        this.listaPicheos = listaPicheos;
        this.stats = null;
    }

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
        if (stats == null) {
            stats = new EstadisticasPitcher(0, 0, 0, 0, 0);
        }
        return stats;
    }

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
