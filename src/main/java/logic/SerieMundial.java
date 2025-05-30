package logic;

import utility.Paths;
import utility.PersistenciaJSON;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class SerieMundial implements Serializable {

    private static SerieMundial instance = null;
    private static final long serialVersionUID = 1L;
    ArrayList<Temporada> temporadas;
    transient ArrayList<Jugador> jugadores;
    ArrayList<Equipo>  equipos;
    private int temporadaActualIndex;// Para llevar control de la temporada actual
    transient private Equipo equipoSeleccionado;
    transient private Jugador jugadorSeleccionado;
    private HashMap<String,String> passwordMap;
    private boolean video = false;
    private int contador = 1;

    private SerieMundial() {
        temporadas = new ArrayList<>();
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();
        passwordMap = new HashMap<>();
        temporadaActualIndex = -1;
    }

    public static SerieMundial getInstance() {
        if (instance == null) {
            instance = PersistenciaJSON.cargarJson(Paths.INSTANCIA, SerieMundial.class);
            if (instance == null)
                instance = new SerieMundial();
        }
        return instance;
    }


    public ArrayList<Jugador> getJugadores() {
        ArrayList<Jugador> todos = new ArrayList<>();
        for (Equipo equipo : equipos) {
            todos.addAll(equipo.getJugadores());
        }
        return todos;
    }

    public HashMap<String,String> getPasswordMap() {
        return passwordMap;
    }

    public boolean getVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setPasswordMap(HashMap<String,String> passwordMap) {
        this.passwordMap = passwordMap;
    }

    public void contadorUp() {
        contador++;
    }

    public int getContador() {
        return contador;
    }


    public ArrayList<Temporada> getCalendarios() {
        return temporadas;
    }

    public ArrayList<Equipo> getEquipos() {
        if (equipos == null) {
            throw new IllegalStateException("La lista de equipos no ha sido inicializada");
        }
        return equipos;
    }

    public void addCalendario(Temporada c) {
        temporadas.add(c);
    }

    public void loadLogoEquipo() {
        for (Equipo equipo : equipos) {
            equipo.loadLogo();
        }
    }


    // Método para guardar la instancia de SerieMundial
    public void guardar() {
        PersistenciaJSON.guardarJson(this, Paths.INSTANCIA);
    }

    public void addEquipo(Equipo e) {
        if (!equipos.contains(e)) {
            equipos.add(e);
        }
    }

    public Temporada getTemporadaActual() {
        // Si no hay temporadas, crea una nueva automáticamente
        if (temporadas.isEmpty()) {
            return iniciarNuevaTemporada(LocalDate.now());
        }
        // Verifica el índice actual
        if (temporadaActualIndex >= 0 && temporadaActualIndex < temporadas.size()) {
            return temporadas.get(temporadaActualIndex);
        }
        // Si el índice es inválido, usa la última temporada
        return temporadas.get(temporadas.size() - 1);
    }

    public Temporada iniciarNuevaTemporada(LocalDate fechaInicio) {

        Temporada nuevaTemporada = new Temporada(equipos, fechaInicio);
        temporadas.add(nuevaTemporada);
        temporadaActualIndex = temporadas.size() - 1;  // Asegura que el índice apunte a la nueva temporada
        return nuevaTemporada;
    }

    public int calcularVictoriasParaCampeonato() {
        int totalEquipos = this.getEquipos().size();
        // Fórmula para torneo todos contra todos (ida y vuelta)
        int totalPartidos = totalEquipos * (totalEquipos - 1);
        return totalPartidos;
    }

    public Equipo detectarCampeon(Partido partidoActual) {
        int victoriasNecesarias = this.calcularVictoriasParaCampeonato();
        Equipo posibleGanador = null;

        // Verificar ambos equipos del partido
        for (Equipo equipo : Arrays.asList(
              partidoActual.getEquipoLocal(),
              partidoActual.getEquipoVisitante())) {

            if (equipo.getJuegosGanados() >= victoriasNecesarias) {
                // Si ya alcanzó las victorias necesarias, ese equipo es el campeón
                posibleGanador = equipo;
                break;  // No es necesario continuar verificando más equipos
            }
        }
        return posibleGanador;
    }


    // Métodos de Búsqueda
    // Obtener todos los jugadores de todos los equipos
    public ArrayList<Jugador> getTodosLosJugadores() {
        ArrayList<Jugador> todosLosJugadores = new ArrayList<>();
        for (Equipo equipo : equipos) {
            todosLosJugadores.addAll(equipo.getJugadores());
        }
        return todosLosJugadores;
    }

    public Equipo buscarEquipoPorNombre(String nombreBuscado) {
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().equalsIgnoreCase(nombreBuscado)) {
                return equipo;
            }
        }
        return null;
    }

    public ArrayList<Bateador> getBateadores() {
        ArrayList<Bateador> bateadores = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Bateador) {
                bateadores.add((Bateador) jugador);
            }
        }
        return bateadores;
    }

    public ArrayList<Pitcher> getPitchers() {
        ArrayList<Pitcher> pitchers = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Pitcher) {
                pitchers.add((Pitcher) jugador);
            }
        }
        return pitchers;
    }

    public void loadJugadores() {
       for (Equipo jugador : equipos) {
          jugadores.addAll(jugador.getJugadores());
       }
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
        return pitchers;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;

    }
    public void setEquipoSeleccionado(Equipo equipo) {
        this.equipoSeleccionado = equipo;
    }

    public Jugador getJugadorSeleccionado() {
        return jugadorSeleccionado;
    }

    public void setJugadorSeleccionado(Jugador jugadorSeleccionado) {
        this.jugadorSeleccionado = jugadorSeleccionado;
    }

    public void eliminarEquipo(Equipo selectedTeam) {
        if (equipos.contains(selectedTeam)) {
            equipos.remove(selectedTeam);
            // Eliminar jugadores asociados al equipo
            for (Jugador jugador : selectedTeam.getJugadores()) {
                jugadores.remove(jugador);
            }
        }
    }

    public void loadJugadorImagen() {
        for (Jugador jugador : jugadores) {
            jugador.loadImageJugador();
        }
    }

}
