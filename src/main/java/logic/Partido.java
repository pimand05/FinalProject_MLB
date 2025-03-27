package logic;

import utility.COMENTARIOS;
import utility.InningKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Partido {

    private LocalDate fecha;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private Resultado resultado;
    private HashMap<InningKey, ArrayList<String>> generalCom = new HashMap<>();
    private int hits1 = 0;
    private int hits2 = 0;
    private int homeRuns1 = 0;
    private int homeRuns2 = 0;
    private int carreras1 = 0;
    private int carreras2 = 0;
    private ArrayList<String> comentarios = new ArrayList<>();


    public Partido(LocalDate fecha, Equipo equipoLocal, Equipo equipoVisitante) {
        this.fecha = fecha;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    //metodos adicionales
    public int getAccion() {
        return (int) (Math.random() * 50 + 1);
    }

    /*
    public void jugarPartido() {
        float inning = 1.0f;
        Jugador jugador;
        int lineUp1 = 1;
        int lineUp2 = 1;
        int accion;
        int strike, bases, ball;
        String comentario ;
        while (inning <= 9.0f) {
            int out1 = 0;
            int out2 = 0;
            bases = 0;
            while (out1 < 3) { //equipo local
                accion = getAccion();
                strike = 0; ball = 0;
                if (accion >= 1 && accion <= 20) {
                    strike++;
                    if (strike == 3) {
                        out1++;
                        comentario = jugador.getNombre() + " " + COMENTARIOS.PONCHE.getMensaje();
                        comentarios.add(comentario);
                        jugador = equipoLocal.getJugador(lineUp1);
                        strike = 0;
                    }
                }
                if (accion >= 21 && accion <= 34) {
                    ball++;
                    if (ball == 4) {
                        bases++;
                        if (bases == 4) {
                            carreras1++;
                            bases = 3;
                        }
                        ball = 0;
                        comentario = jugador.getNombre() + " " + COMENTARIOS.BASExBOLA.getMensaje();
                        comentarios.add(comentario);
                        jugador = equipoLocal.getJugador(lineUp1);
                    }
                }
                if (accion >= 35 && accion <= 46) {
                    hits1++;
                    bases++;
                    if (bases == 4) {
                        carreras1++;
                        bases = 3;
                    }
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HIT.getMensaje();
                    comentarios.add(comentario);
                    jugador = equipoLocal.getJugador(lineUp1);
                }
                if (accion > 46 && accion <= 50) {
                    homeRuns1++;
                    carreras1 += bases;
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HOMERUN.getMensaje();
                    comentarios.add(comentario);
                    jugador = equipoLocal.getJugador(lineUp1);
                }
                lineUp1 = (lineUp1 % 9) + 1;
            }

            generalCom.put(inning, new ArrayList<>(comentarios));
            inning += 0.5f;
            comentarios.clear();

            bases = 0;
            while (out2 < 3) { //equipo Visistante
                accion = getAccion();
                strike = 0; ball = 0;
                jugador = equipoVisitante.getJugador(lineUp2);
                if (accion >= 1 && accion <= 20) {
                    strike++;
                    if (strike == 3) {
                        out2++;
                        comentario = jugador.getNombre() + " " + COMENTARIOS.PONCHE.getMensaje();
                        comentarios.add(comentario);
                        strike = 0;
                    }
                }
                if (accion > 20 && accion <= 34) {
                    ball++;
                    if (ball == 4) {
                        bases++;
                        if (bases == 4) {
                            carreras2++;
                            bases = 3;
                        }
                        ball = 0;
                        comentario = jugador.getNombre() + " " + COMENTARIOS.BASExBOLA.getMensaje();
                        comentarios.add(comentario);
                    }
                }
                if (accion > 34 && accion <= 46) {
                    hits2++;
                    bases++;
                    if (bases == 4) {
                        carreras2++;
                        bases = 3;
                    }
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HIT.getMensaje();
                    comentarios.add(comentario);
                }
                if (accion > 46 && accion <= 50) {
                    homeRuns2++;
                    carreras2 += bases;
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HOMERUN.getMensaje();
                    comentarios.add(comentario);
                }
                lineUp2 = (lineUp2 % 9) + 1;
            }
            inning += 1.0f;
            generalCom.put(inning, new ArrayList<>(comentarios));
            comentarios.clear();
        }
    }
     */

    public void jugarJuego(Equipo equipo,String referencia) {
        int inning = 1;
        Bateador jugador;
        Pitcher pitcher;
        int lineUp = 1;
        int accion;
        int strike, bases, ball;
        String comentario;
        while (inning <= 9) {
            int out = 0;
            strike = 0; ball = 0; bases = 0;
            while (out < 3) {
                accion = getAccion();
                jugador = (Bateador) equipo.getJugador(inning);
                pitcher = equipo.getPitcher();
                pitcher.incrementarTurnos();
                jugador.incrementTurnos();
                if (accion >= 1 && accion <= 20) { //Striker || out
                    strike++;
                    if (strike == 3) {
                        out++;
                        pitcher.getStats().incrementarponchesLanzados();
                        comentario = jugador.getNombre() + " " + COMENTARIOS.PONCHE.getMensaje();
                        comentarios.add(comentario);
                        strike = 0; ball = 0;
                    }
                }
                if (accion >= 21 && accion <= 34) { //Bola
                    ball++;
                    if (ball == 4) {
                        bases++;
                        if (bases == 4) {
                            carreras1++;
                            bases = 3;
                        }
                        strike = 0; ball = 0;
                        comentario = jugador.getNombre() + " " + COMENTARIOS.BASExBOLA.getMensaje();
                        comentarios.add(comentario);
                    }
                }
                if (accion >= 35 && accion <= 46) { //Hit
                    hits1++;
                    bases++;
                    if (bases == 4) {
                        carreras1++;
                        strike = 0; ball = 0;
                        bases = 3;
                    }
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HIT.getMensaje();
                    jugador.getStats().incrementarHits();
                    comentarios.add(comentario);
                }
                if (accion > 46 ) { //HomeRun
                    homeRuns1++;
                    carreras1 += bases;
                    strike = 0; ball = 0; bases = 0;
                    comentario = jugador.getNombre() + " " + COMENTARIOS.HOMERUN.getMensaje();
                    jugador.getStats().incrementarHomeRuns();
                    comentarios.add(comentario);
                }
                lineUp = (lineUp % 9) + 1;
            }
            generalCom.put(new InningKey(inning,referencia), new ArrayList<>(comentarios));
            inning++;
            comentarios.clear();
        }
    }
}
