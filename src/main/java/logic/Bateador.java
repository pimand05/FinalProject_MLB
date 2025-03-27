package logic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bateador extends Jugador {
    private String posicion;
    private EstadisticasBateador stats;
    private int turnos = 0;
    private boolean lesionado = false;

    public Bateador(String nombre, LocalDate fNacimiento, float altura, ArrayList<Lesion> historialLesiones,
                    String posicion, EstadisticasBateador stats) {
        super(nombre, fNacimiento, altura, historialLesiones);
        this.posicion = posicion;
        this.stats = stats;
    }

    public String getPosicion() {
        return posicion;
    }

    public EstadisticasBateador getStats() {
        return stats;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public void setStats(EstadisticasBateador stats) {
        this.stats = stats;
    }

    public void incrementTurnos() {
        turnos++;
    }

    public boolean isLesionado() {
        return lesionado;
    }

    public void setLesionado(boolean lesionado) {
        this.lesionado = lesionado;
    }

    @Override
    public void actualizarEstadisticas(Partido partido) {

    }


}
