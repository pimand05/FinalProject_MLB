package logic;

import java.io.Serializable;

public class EstadisticasPitcher implements Serializable  {
    private static final long serialVersionUID = 1L;
    private int entradasLanzadas;
    private int ponchesLanzados;
    private int juegosJugados;
    private int basePorBola;
    private int carrerasLimpiasPermitidas;
    private float efectivas;

    //Constructor


    public EstadisticasPitcher(int entradasLanzadas, int ponchesLanzados, int juegosJugados, int carrerasLimpiasPermitidas, float efectivas) {
        this.entradasLanzadas = entradasLanzadas;
        this.ponchesLanzados = ponchesLanzados;
        this.juegosJugados = juegosJugados;
        this.carrerasLimpiasPermitidas = carrerasLimpiasPermitidas;
        this.efectivas = efectivas;
    }

    //Setters y Getters
    public int getEntradasLanzadas() {
        return entradasLanzadas;
    }

    public void setEntradasLanzadas(int entradasLanzadas) {
        this.entradasLanzadas = entradasLanzadas;
    }

    public int getPonchesLanzados() {
        return ponchesLanzados;
    }

    public void setPonchesLanzados(int ponchesLanzados) {
        this.ponchesLanzados = ponchesLanzados;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public void setJuegosJugados(int juegosJugados) {
        this.juegosJugados = juegosJugados;
    }

    public int getBasePorBola() {
        return basePorBola;
    }

    public void setBasePorBola(int basePorBola) {
        this.basePorBola = basePorBola;
    }



    //Metodos
    public float promedioPonches() {
        return (float) ponchesLanzados / entradasLanzadas;
    }

    public void incrementarponchesLanzados() {
        ponchesLanzados++; //crear metodo para incrementar ponches lanzados
    }

    public Float calcularERA() {
        return (carrerasLimpiasPermitidas / (float) entradasLanzadas) * 9;
    }
}

