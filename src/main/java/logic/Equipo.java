package logic;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void setJuegosGanados(int juegosGanados) {
        this.juegosGanados = juegosGanados;
    }

    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public void setJuegosPerdidos(int juegosPerdidos) {
        this.juegosPerdidos = juegosPerdidos;
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
            throw new IllegalStateException("El lineup no ha sido configurado");
        }
        return lineup.get(bateadorActualIndex);
    }

    public void avanzarBateador() {
        bateadorActualIndex = (bateadorActualIndex % 9) + 1;
    }

    public Pitcher getPitcherActual() {
        if (pitcherActual == null || pitcherActual.getStats().getEntradasLanzadas() > 100) {
            cambiarPitcher();
        }
        return pitcherActual;
    }

    private void cambiarPitcher() {
        if (!relevistas.isEmpty()) {
            pitcherActual = relevistas.remove(0);
        } else {
            throw new IllegalStateException("No hay relevistas disponibles");
        }
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
        List<Bateador> bateadores = getBateadores();
        List<Pitcher> pitchers = getPitchers();

        if (bateadores.size() < 9) {
            throw new IllegalStateException("No hay suficientes bateadores (se necesitan al menos 9)");
        }

        if (pitchers.isEmpty()) {
            throw new IllegalStateException("No hay pitchers en el equipo");
        }

        // Configurar lineup con los primeros 9 bateadores
        configurarLineup(bateadores.subList(0, 9));

        // Configurar pitchers (el primero como abridor, los demás como relevistas)
        pitcherActual = pitchers.get(0);
        if (pitchers.size() > 1) {
            relevistas.addAll(pitchers.subList(1, pitchers.size()));
        }
    }

    public Image getLogo() {
        return new Image(new File(rutaLogo).toURI().toString());
    }
}
