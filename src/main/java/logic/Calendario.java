package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calendario {

    private static Calendario instance = null;
    private List<Partido> partidos;

    public Calendario(List<Equipo> equipos, LocalDate fechaInicio) {
        this.partidos = generarCalendario(equipos, fechaInicio);
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public static Calendario getInstance(List<Equipo> equipos, LocalDate fechaInicio) {
        if (instance == null) {
            instance = new Calendario(equipos, fechaInicio);
        }
        return instance;
    }

    private List<Partido> generarCalendario(List<Equipo> equipos, LocalDate fechaInicio) {
        List<Partido> calendario = new ArrayList<>();

        if (equipos.size() < 2) {
            return calendario; // No se pueden generar partidos con menos de 2 equipos
        }

        int diasUsados = 0;
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Equipo equipoA = equipos.get(i);
                Equipo equipoB = equipos.get(j);

                // Partido 1: Equipo A en casa
                calendario.add(new Partido(fechaInicio.plusDays(diasUsados), equipoA, equipoB));
                diasUsados++;

                // Partido 2: Equipo B en casa
                calendario.add(new Partido(fechaInicio.plusDays(diasUsados), equipoA, equipoB));
                diasUsados++;
            }
        }

        // Mezclamos los partidos para que no siempre sean en orden predecible
        Collections.shuffle(calendario);

        return calendario;
    }



}
