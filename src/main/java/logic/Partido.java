package logic;

import utility.COMENTARIOS;
import utility.InningKey;

import java.time.LocalDate;
import java.util.*;

public class Partido {
    // Constantes para rangos de acciones
    private static final int STRIKE_MIN = 1;
    private static final int STRIKE_MAX = 15;
    private static final int BALL_MIN = 16;
    private static final int BALL_MAX = 30;
    private static final int HIT_MIN = 31;
    private static final int HIT_MAX = 40;
    private static final int HOMERUN_MIN = 41;
    private static final int HOMERUN_MAX = 44;
    //AGREGAR OUTS

    // Atributos básicos del partido
    private final LocalDate fecha;
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private final String estadio;
    private Resultado resultado;
    private boolean partidoTerminado = false;

    // Estado actual del juego
    private int inningActual = 1;
    private boolean esTopInning = true;
    private int outs = 0;
    private int strikes = 0;
    private int bolas = 0;
    private boolean[] bases = new boolean[3]; // [0] = 1ra, [1] = 2da, [2] = 3ra

    // Estadísticas del partido
    private int hitsLocal = 0;
    private int hitsVisitante = 0;
    private int homeRunsLocal = 0;
    private int homeRunsVisitante = 0;
    private int carrerasLocal = 0;
    private int carrerasVisitante = 0;
    private int erroresLocal = 0;
    private int erroresVisitante = 0;
    private int[][] carrerasPorInning = new int[2][9]; // [0] = visitante, [1] = local

    // Registro del juego
    private final Map<InningKey, List<String>> comentariosPorInning = new HashMap<>();
    private final List<String> resumenPartido = new ArrayList<>();
    private final List<String> comentariosTemporales = new ArrayList<>();

    public Partido(LocalDate fecha, String estadio, Equipo equipoLocal, Equipo equipoVisitante) {
        if (fecha == null || equipoLocal == null || equipoVisitante == null) {
            throw new IllegalArgumentException("Fecha, estadio y equipos no pueden ser nulos");
        }
        this.fecha = fecha;
        this.estadio = estadio;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        //inicializarEstadisticasJugadores();
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstadio() {
        return estadio;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public boolean isPartidoTerminado() {
        return partidoTerminado;
    }

    public int getInningActual() {
        return inningActual;
    }

    public boolean isEsTopInning() {
        return esTopInning;
    }

    public int getBolas() {
        return bolas;
    }

    public int getStrikes() {
        return strikes;
    }

    public int getOuts() {
        return outs;
    }

    public int getHitsLocal() {
        return hitsLocal;
    }

    public int getHitsVisitante() {
        return hitsVisitante;
    }

    public int getHomeRunsLocal() {
        return homeRunsLocal;
    }

    public int getHomeRunsVisitante() {
        return homeRunsVisitante;
    }

    public int getCarrerasLocal() {
        return carrerasLocal;
    }

    public int getCarrerasVisitante() {
        return carrerasVisitante;
    }

    public int getErroresLocal() {
        return erroresLocal;
    }

    public int getErroresVisitante() {
        return erroresVisitante;
    }

    public List<String> getResumenPartido() {
        return new ArrayList<>(resumenPartido);
    }

    public Map<InningKey, List<String>> getComentariosPorInning() {
        return new HashMap<>(comentariosPorInning);
    }

    public int[] getCarrerasPorInningVisitante() {
        return carrerasPorInning[0];
    }

    public int[] getCarrerasPorInningLocal() {
        return carrerasPorInning[1];
    }

    public boolean hayCorredorEn(int base) {
        return base >= 1 && base <= 3 && bases[base - 1];
    }

    // Inicializar las estadisticas de los jugadores


    public void simularSiguienteJugada() {
        if (partidoTerminado) return;

        if (outs >= 3) {
            cambiarMedioInning();
            return;
        }

        Equipo equipoAlBat = esTopInning ? equipoVisitante : equipoLocal;
        Equipo equipoDefensor = esTopInning ? equipoLocal : equipoVisitante;

        Bateador bateador = (Bateador) equipoAlBat.getBateadorActual();
        Pitcher pitcher = equipoDefensor.getPitcherActual();

        procesarTurnoAlBat(bateador, pitcher, !esTopInning);

        // Rotar al siguiente bateador
        if (turnoTerminado(bateador)) {
            equipoAlBat.avanzarBateador();
        }

        // Verificar si es el final del partido (9 innings completos)
        if (inningActual > 9 && carrerasLocal != carrerasVisitante) {
            partidoTerminado = true;
            determinarResultado();
        }
    }

    private void cambiarMedioInning() {
        outs = 0;
        strikes = 0;
        bolas = 0;
        limpiarBases();

        if (esTopInning) {
            esTopInning = false; // Cambia a Bottom (local)

            // Guardar comentarios del top del inning
            guardarComentariosInning(inningActual, "TOP");
        } else {
            esTopInning = true;
            inningActual++; // Pasa al siguiente inning

            // Guardar comentarios del bottom del inning
            guardarComentariosInning(inningActual - 1, "BOTTOM");
        }
    }

    private void procesarTurnoAlBat(Bateador bateador, Pitcher pitcher, boolean esLocal) {
        int accion = getAccionAleatoria();

        if (accion >= STRIKE_MIN && accion <= STRIKE_MAX) {
            procesarStrike(bateador, pitcher, esLocal);
        } else if (accion >= BALL_MIN && accion <= BALL_MAX) {
            procesarBola(bateador, pitcher, esLocal);
        } else if (accion >= HIT_MIN && accion <= HIT_MAX) {
            procesarHit(bateador, esLocal);
        } else if (accion >= HOMERUN_MIN && accion <= HOMERUN_MAX) {
            procesarHomeRun(bateador, esLocal);
        }
    }

    private void procesarStrike(Bateador bateador, Pitcher pitcher, boolean esLocal) {
        pitcher.incrementarTurnos();
        bateador.incrementTurnos();
        pitcher.getStats().incrementarponchesLanzados();
        strikes++;

        if (strikes >= 3) {
            outs++;
            strikes = 0;
            bolas = 0;
            agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.PONCHE.getMensaje()));
        } else {
            agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.STRIKE.getMensaje()));
        }
    }

    private void procesarBola(Bateador bateador, Pitcher pitcher, boolean esLocal) {
        bateador.incrementTurnos();
        pitcher.incrementarTurnos();
        bolas++;

        if (bolas >= 4) {
            int carreras = avanzarCorredores(1, esLocal);
            bases[0] = true;
            registrarCarreras(carreras, esLocal);
            bolas = 0;
            strikes = 0;

            agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.BASExBOLA.getMensaje()));

            // Pasa el siguiente bateador
            Equipo equipoAlBat = esTopInning ? equipoVisitante : equipoLocal;
            equipoAlBat.avanzarBateador();
        } else {
            agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.BOLA.getMensaje()));
        }

    }

    private void procesarHit(Bateador bateador, boolean esLocal) {
        bateador.incrementTurnos();
        bateador.getStats().incrementarHits();

        if (esLocal) {
            hitsLocal++;
        } else {
            hitsVisitante++;
        }

        int carreras = avanzarCorredores(1, esLocal);
        bases[0] = true;
        registrarCarreras(carreras, esLocal);

        agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.HIT.getMensaje()));
    }

    private void procesarHomeRun(Bateador bateador, boolean esLocal) {
        bateador.incrementTurnos();
        bateador.getStats().incrementarHomeRuns();

        if (esLocal) {
            homeRunsLocal++;
        } else {
            homeRunsVisitante++;
        }

        int carreras = 1 + contarCorredores();
        registrarCarreras(carreras, esLocal);
        limpiarBases();

        agregarComentario(String.format("%s %s", bateador.getNombre(), COMENTARIOS.HOMERUN.getMensaje()));
    }

    private int avanzarCorredores(int bases, boolean esLocal) {
        int carreras = 0;

        if (this.bases[2]) { // Corredor en tercera anota
            carreras++;
            this.bases[2] = false;
        }
        if (this.bases[1]) { // Corredor en segunda avanza a tercera
            this.bases[2] = true;
            this.bases[1] = false;
        }
        if (this.bases[0]) { // Corredor en primera avanza a segunda
            this.bases[1] = true;
            this.bases[0] = false;
        }

        // Si el bateador caminó o pegó hit, ocupa primera base
        this.bases[0] = true;

        // Registrar en el marcador por inning
        registrarCarreras(carreras, esLocal);

        return carreras;
    }

    // Registro de las Carreras Individuales
    private void registrarCarreras(int carreras, boolean esLocal) {
        if (carreras > 0) {
            if (esLocal) {
                carrerasLocal += carreras;
                carrerasPorInning[1][inningActual - 1] += carreras;
            } else {
                carrerasVisitante += carreras;
                carrerasPorInning[0][inningActual - 1] += carreras;
            }
        }
    }

    private boolean turnoTerminado(Bateador bateador) {
        // Verificar las condiciones que terminan un turno al bate
        return outs >= 3 ||                // 3 outs terminan el inning
              strikes >= 3 ||             // 3 strikes es out
              bolas >= 4 ||               // 4 bolas es base por bola
              ultimaJugadaFueHit() ||     // Hit sencillo
              ultimaJugadaFueHomeRun();   // Home Run
    }

    private boolean ultimaJugadaFueHit() {
        return !comentariosTemporales.isEmpty() &&
              comentariosTemporales.get(comentariosTemporales.size() - 1).contains("ha conectado un hit");
    }

    private boolean ultimaJugadaFueHomeRun() {
        return !comentariosTemporales.isEmpty() &&
              comentariosTemporales.get(comentariosTemporales.size() - 1).contains("ha conectado un cuadrangular");
    }

    private int contarCorredores() {
        int count = 0;
        for (boolean base : bases) {
            if (base) count++;
        }
        return count;
    }

    private void limpiarBases() {
        bases = new boolean[3];
    }

    private int getAccionAleatoria() {
        return (int) (Math.random() * HOMERUN_MAX + 1);
    }

    private void agregarComentario(String comentario) {
        comentariosTemporales.add(comentario);
        resumenPartido.add(comentario);
    }

    private void guardarComentariosInning(int inning, String mitadInning) {
        comentariosPorInning.put(new InningKey(inning, mitadInning), new ArrayList<>(comentariosTemporales));
        comentariosTemporales.clear();
    }

    private void determinarResultado() {
        this.resultado = new Resultado(carrerasLocal, carrerasVisitante);
        resumenPartido.add("\nResultado final: " + equipoLocal.getNombre() + " " + carrerasLocal +
              " - " + carrerasVisitante + " " + equipoVisitante.getNombre());
    }

    public class Resultado {
        private final int carrerasLocal;
        private final int carrerasVisitante;

        public Resultado(int carrerasLocal, int carrerasVisitante) {
            this.carrerasLocal = carrerasLocal;
            this.carrerasVisitante = carrerasVisitante;
        }

        public boolean esVictoriaLocal() {
            return carrerasLocal > carrerasVisitante;
        }

        public Equipo getEquipoGanador() {
            return esVictoriaLocal() ? equipoLocal : equipoVisitante;
        }

        public Equipo getEquipoPerdedor() {
            return esVictoriaLocal() ? equipoVisitante : equipoLocal;
        }

        public int getCarrerasGanador() {
            return esVictoriaLocal() ? carrerasLocal : carrerasVisitante;
        }

        public int getCarrerasPerdedor() {
            return esVictoriaLocal() ? carrerasVisitante : carrerasLocal;
        }

        @Override
        public String toString() {
            return equipoLocal.getNombre() + " " + carrerasLocal + " - " +
                  carrerasVisitante + " " + equipoVisitante.getNombre();
        }
    }
}