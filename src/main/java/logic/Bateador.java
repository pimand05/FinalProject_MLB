package logic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bateador extends Jugador {
    private String posicion;
    private EstadististicasBateador stats;

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

    @Override
    public void actualizarEstadisticas(Partido partido) {

    }
