package logic;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Equipo  implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String estadio;
    private String ciudad;
    private String historia;
    private Boolean calificado;
    private int juegosGanados;
    private int juegosPerdidos;
    private String  colorPrimario;
    private String colorSecundario;
    private String rutaLogo;
    private transient Image logo;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Partido> partidosJugados;

    //Manejos de los Partidos
    private int bateadorActualIndex = 1;
    private Pitcher pitcherActual;
    private HashMap<Integer, Bateador> lineup = new HashMap<>();
    private List<Pitcher> relevistas = new ArrayList<>();

    //Constructor


    public Equipo(String nombre, String estadio, String ciudad, String historia, String colorPrimario, String colorSecundario, String rutaLogo) {
        this.nombre = nombre;
        this.estadio = estadio;
        this.ciudad = ciudad;
        this.colorPrimario = colorPrimario;
        this.colorSecundario = colorSecundario;
        this.historia = historia;
        this.rutaLogo = rutaLogo;
        this.jugadores = new ArrayList<>();
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
    //Implemente Getters y Setters para ciudad
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public void incrementarJuegosGanados() {
        this.juegosGanados++;
    }

    public void incrementarJuegosPerdidos() {
        this.juegosPerdidos++;
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

    public String getColorPrimario() {
        return colorPrimario;
    }

    public void setColorPrimario(String colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public String getColorSecundario() {
        return colorSecundario;
    }

    public void setColorSecundario(String colorSecundario) {
        this.colorSecundario = colorSecundario;
    }

    public String getrutaLogo() {
        return rutaLogo;
    }

    public void setrutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public int getBateadorActualIndex() {
        return bateadorActualIndex;
    }

    public void setBateadorActualIndex(int bateadorActualIndex) {
        this.bateadorActualIndex = bateadorActualIndex;
    }

    public void setPitcherActual(Pitcher pitcherActual) {
        this.pitcherActual = pitcherActual;
    }

    public HashMap<Integer, Bateador> getLineup() {
        return lineup;
    }

    public List<Pitcher> getRelevistas() {
        return relevistas;
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

//    public HashMap<Integer, Bateador> getLineup() {
//        return lineup;
//    }

    public Bateador getBateadorActual() {
        if (lineup.isEmpty()) {
            throw new IllegalStateException("El lineup no ha sido configurado ");
        }
        return lineup.get(bateadorActualIndex);
    }

    public void avanzarBateador() {
        bateadorActualIndex = (bateadorActualIndex % 9) + 1;
    }


    public Pitcher getPitcherActual() {
        if (pitcherActual == null ||
              (pitcherActual.getStats() != null &&
                    pitcherActual.getStats().getEntradasLanzadas() > 100)) {

            try {
                cambiarPitcher();
            } catch (IllegalStateException e) {
                if (pitcherActual != null) {
                    System.out.println("¡" + pitcherActual.getNombre() + " continúa lanzando por falta de opciones!");
                    return pitcherActual;
                }
                throw e;
            }
        }
        return pitcherActual;
    }

    public void cambiarPitcher() {
        // Filtrar relevistas disponibles
        List<Pitcher> disponibles = relevistas.stream()
              .filter(Objects::nonNull)
              .sorted(Comparator.comparingInt(p -> p.getTipoDeLanzador().equals("Cerrador") ? 0 : 1))
              .collect(Collectors.toList());

        if (!disponibles.isEmpty()) {
            Pitcher nuevoPitcher = disponibles.get(0);
            relevistas.remove(nuevoPitcher);

            if (pitcherActual != null) {
                relevistas.add(pitcherActual);
            }

            pitcherActual = nuevoPitcher;
            System.out.println("Nuevo pitcher: " + nuevoPitcher.getNombre());
            return;
        }

        List<Pitcher> todosPitchers = getPitchers().stream()
              .filter(p -> p != pitcherActual)
              .collect(Collectors.toList());

        if (!todosPitchers.isEmpty()) {
            pitcherActual = todosPitchers.get(0);
            System.out.println("Pitcher de emergencia: " + pitcherActual.getNombre());
            return;
        }

        throw new IllegalStateException("No hay pitchers disponibles (lesionados/cansados)");
    }

    public void configurarLineup(List<Bateador> bateadores) {
        if (bateadores == null) {
            throw new IllegalArgumentException("La lista de bateadores no puede ser nula");
        }
        if (bateadores.size() < 9) {
            throw new IllegalArgumentException("Se necesitan al menos 9 bateadores");
        }
        if (bateadores.contains(null)) {
            throw new IllegalArgumentException("La lista de bateadores no puede contener valores nulos");
        }

        lineup.clear();
        for (int i = 0; i < 9; i++) {
            lineup.put(i+1, bateadores.get(i));
        }
        bateadorActualIndex = 1; // Resetear el índice
    }

    public void agregarRelevista(Pitcher pitcher) {
        if (pitcher == null) {
            throw new IllegalArgumentException("El pitcher no puede ser nulo");
        }

        relevistas.add(pitcher);
        if (pitcherActual == null) {
            pitcherActual = pitcher;
        }
    }

    public int getCarrerasTotales() {
        int total = 0;
        for (Bateador b : lineup.values()) {
            total += b.getStats().getCarreras();
        }
        return total;
    }

    public int getHitsTotales() {
        int total = 0;
        for (Bateador b : lineup.values()) {
            total += b.getStats().getHits();
        }
        return total;
    }

    public Bateador cambiarBateadorLesionado() {
        int intentos = 0;
        int indiceInicial = bateadorActualIndex;

        do {
            avanzarBateador();
            Bateador posibleReemplazo = lineup.get(bateadorActualIndex);

            if (posibleReemplazo != null && !posibleReemplazo.getLesion().isActiva()) {
                return posibleReemplazo;
            }

            intentos++;
        } while (intentos < 8 && bateadorActualIndex != indiceInicial);

        return null;
    }

    public Pitcher cambiarPitcherLesionado() {
        List<Pitcher> relevistasDisponibles = new ArrayList<>(relevistas);

        for (Pitcher relevista : relevistasDisponibles) {
            if (relevista != null && !relevista.getLesion().isActiva()) {
                relevistas.remove(relevista);
                Pitcher antiguoPitcher = pitcherActual;
                pitcherActual = relevista;

                if (antiguoPitcher != null && !antiguoPitcher.getLesion().isActiva()) {
                    relevistas.add(antiguoPitcher);
                }

                return relevista;
            }
        }

        return null; // No hay relevistas disponibles
    }

    // Método para buscar jugador por nombre
    public Jugador buscarJugador(String nombre) {
        return jugadores.stream()
              .filter(j -> j.getNombre().equalsIgnoreCase(nombre))
              .findFirst()
              .orElse(null);
    }

    // buscarJugador alt
    /*
    public Jugador buscarJugador(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }
     */


    // Método para obtener todos los bateadores
    public List<Bateador> getBateadores() {
        List<Bateador> bateadores = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j instanceof Bateador) {
                bateadores.add((Bateador) j);
            }
        }
        return bateadores;
    }

    // Método para obtener todos los pitchers
    public List<Pitcher> getPitchers() {
        List<Pitcher> pitchers = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j instanceof Pitcher) {
                pitchers.add((Pitcher) j);
            }
        }
        return pitchers;
    }

    public void configurarEquipo() {
        // Filtrar bateadores disponibles
        List<Bateador> bateadoresDisponibles = new ArrayList<>();
        List<Bateador> todosBateadores = getBateadores();

        for (Bateador bateador : todosBateadores) {
            if (!bateador.getLesion().isActiva()) {
                bateadoresDisponibles.add(bateador);
            }
        }

        // Filtrar pitchers disponibles
        List<Pitcher> pitchersDisponibles = new ArrayList<>();
        List<Pitcher> todosPitchers = getPitchers();

        for (Pitcher pitcher : todosPitchers) {
            if (!pitcher.getLesion().isActiva()) {
                pitchersDisponibles.add(pitcher);
            }
        }

        // Verificar disponibilidad de bateadores
        if (bateadoresDisponibles.size() < 9) {
            int totalBateadores = todosBateadores.size();
            int lesionados = totalBateadores - bateadoresDisponibles.size();
            int faltantes = 9 - bateadoresDisponibles.size();

            throw new IllegalStateException(
                  "No hay suficientes bateadores disponibles. Faltan " + faltantes +
                        " (Total: " + totalBateadores +
                        ", Lesionados: " + lesionados + ")"
            );
        }

        // Verificar disponibilidad de pitchers
        if (pitchersDisponibles.isEmpty()) {
            int totalPitchers = todosPitchers.size();
            int lesionados = totalPitchers - pitchersDisponibles.size();

            throw new IllegalStateException(
                  "No hay pitchers disponibles (Total: " + totalPitchers +
                        ", Lesionados: " + lesionados + ")"
            );
        }

        // Configurar lineup con los primeros 9 bateadores disponibles
        List<Bateador> lineup = bateadoresDisponibles.subList(0, 9);
        configurarLineup(lineup);

        // Configurar pitchers
        pitcherActual = pitchersDisponibles.get(0);

        if (pitchersDisponibles.size() > 1) {
            relevistas.clear();
            for (int i = 1; i < pitchersDisponibles.size(); i++) {
                relevistas.add(pitchersDisponibles.get(i));
            }
        }
    }


    public Image getLogo() {
        return new Image(new File(rutaLogo).toURI().toString());
    }
}
