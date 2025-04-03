package logic;

import utility.Paths;
import utility.Persistencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SerieMundial implements Serializable {

    private static SerieMundial instance = null;
    private static final long serialVersionUID = 1L;
    ArrayList<Temporada> temporadas;
    ArrayList<Jugador> jugadores;
    ArrayList<Equipo>  equipos;
    private int temporadaActualIndex;  // Para llevar control de la temporada actual

    private SerieMundial() {
        temporadas = new ArrayList<>();
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();
        temporadaActualIndex = -1;
    }

    public static SerieMundial getInstance() {
        if (instance == null) {
            instance = Persistencia.cargarObjeto(Paths.INSTANCIA);
            if (instance == null)
                instance = new SerieMundial();
        }
        return instance;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<Temporada> getCalendarios() {
        return temporadas;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void addCalendario(Temporada c) {
        temporadas.add(c);
    }


    // Método para guardar la instancia de SerieMundial
    public void guardar() {
        Persistencia.guardarObjeto(this, Paths.INSTANCIA);
    }

//    public void addJugador(Jugador j) {
//        jugadores.add(j);
//    }

    public void addEquipo(Equipo e) {
        equipos.add(e);
    }


    // Método para iniciar una nueva temporada
    public void iniciarNuevaTemporada(LocalDate fechaInicio) {
        if (equipos.size() < 2) {
            throw new IllegalStateException("Se necesitan al menos 2 equipos para generar un calendario");
        }

        Temporada nuevaTemporada = new Temporada(equipos, fechaInicio);
        temporadas.add(nuevaTemporada);
        temporadaActualIndex = temporadas.size() - 1;  // Establece como temporada actual
    }

    public Temporada getTemporadaActual() {
        if (temporadaActualIndex >= 0 && temporadaActualIndex < temporadas.size()) {
            return temporadas.get(temporadaActualIndex);
        }
        return null;
    }

    // Métodos de Búsqueda
    public Equipo buscarEquipoPorNombre(String nombreBuscado) {
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreBuscado)) {
                return equipo;
            }
        }
        return null;
    }

    public Jugador buscarJugadorPorNombre(String nombreBuscado) {
        for (Jugador jugador: jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombreBuscado)) {
                return jugador;
            }
        }
        return null;
    }

    public Equipo buscarEquipoDelJugador(Jugador jugador) {
        for (Equipo equipo : equipos) {
            if (equipo.getJugadores().contains(jugador)) {
                return equipo;
            }
        }
        return null;
    }

    // Top Jugadores
    //Bateadores Con Mas Carreras
    public List<Bateador> ordenarBateadoresPorCarreras() {
        List<Bateador> bateadores = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j instanceof Bateador) {
                bateadores.add((Bateador)j);
            }
        }

        Collections.sort(bateadores, new Comparator<Bateador>() {
            public int compare(Bateador b1, Bateador b2) {
                return Integer.compare(b2.getStats().getCarreras(), b1.getStats().getCarreras());
            }
        });

        return bateadores;
    }

    //Bateadores Con Mayor AVG
    public List<Bateador> ordenarBateadoresPorAvg() {
        List<Bateador> bateadores = new ArrayList<>();

        for (Jugador j : jugadores) {
            if (j instanceof Bateador) {
                bateadores.add((Bateador)j);
            }
        }

        Collections.sort(bateadores, new Comparator<Bateador>() {
            public int compare(Bateador b1, Bateador b2) {
                return Float.compare(b2.getStats().getPromedioBateo(), b1.getStats().getPromedioBateo());
            }
        });

        return bateadores;
    }

    // Pitchers Con Mayor Efectividad
    public List<Pitcher> ordenarPitchersPorEfectividad() {
        List<Pitcher> pitchers = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j instanceof Pitcher) {
                pitchers.add((Pitcher)j);
            }
        }

//        Collections.sort(pitchers, new Comparator<Pitcher>() {
//            public int compare(Pitcher p1, Pitcher p2) {
//                return Float.compare(p2.getStats().getEfectivas, p1.getStats().getEfectivas());
//            }
//        });
       return pitchers;
    }


    // En caso de filtrar una x cantidad de jugadores (OPCIONAL)
//    public List<Pitcher> obtenerTopPitchers(int cantidad) {
//        List<Pitcher> ordenados = ordenarPitchersPorEfectividad();
//        return aplicarFiltroCantidad(ordenados, cantidad);
//    }
//
//    public List<Bateador> obtenerTopBateadoresCarreras(int cantidad) {
//        List<Bateador> ordenados = ordenarBateadoresPorCarreras();
//        return aplicarFiltroCantidad(ordenados, cantidad);
//    }
//
//    public List<Bateador> obtenerTopBateadoresAvg(int cantidad) {
//        List<Bateador> ordenados = ordenarBateadoresPorAvg();
//        return aplicarFiltroCantidad(ordenados, cantidad);
//    }
//
//    private <T> List<T> aplicarFiltroCantidad(List<T> lista, int cantidad) {
//        if (cantidad <= 0 || cantidad >= lista.size()) {
//            return lista;
//        }
//        return lista.subList(0, cantidad);
//    }



}
