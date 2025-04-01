package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Temporada {

    private static Temporada instance = null;
    private ArrayList<Partido> partidos;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    public Temporada(ArrayList<Equipo> equipos, LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        this.partidos = new ArrayList<>(generarCalendario(equipos, fechaInicio));
        this.activa = true;
        if (!partidos.isEmpty()) {
            this.fechaFin = partidos.get(partidos.size()-1).getFecha();
        }
    }

    public ArrayList<Partido> getPartidos() {
        return partidos;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void finalizarTemporada() {
        this.activa = false;
    }

//    public static Temporada getInstance(List<Equipo> equipos, LocalDate fechaInicio) {
//        if (instance == null) {
//            instance = new Temporada(equipos, fechaInicio);
//        }
//        return instance;
//    }

    public Partido getPartidoDelDia(LocalDate fecha) {
        for (Partido partido : partidos) {
            if (partido.getFecha().equals(fecha)) {
                return partido;
            }
        }
        return null;
    }

    private ArrayList<Partido> generarCalendario(ArrayList<Equipo> equipos, LocalDate fechaInicio) {
        ArrayList<Partido> calendario = new ArrayList<>();

        if (equipos == null || equipos.size() < 2) {
            return calendario;
        }

        int diasUsados = 0;
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Equipo equipoA = equipos.get(i);
                Equipo equipoB = equipos.get(j);

                // Partido ida
                calendario.add(new Partido(fechaInicio.plusDays(diasUsados), equipoA.getEstadio(), equipoA, equipoB));
                diasUsados++;

                // Partido vuelta
                calendario.add(new Partido(fechaInicio.plusDays(diasUsados), equipoB.getEstadio(), equipoB, equipoA));
                diasUsados++;
            }
        }

        Collections.shuffle(calendario);
        return calendario;
    }
}
