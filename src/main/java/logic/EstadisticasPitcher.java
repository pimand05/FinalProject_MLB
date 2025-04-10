package logic;

import java.io.Serializable;

public class EstadisticasPitcher implements Serializable  {
    private static final long serialVersionUID = 1L;
    private int entradasLanzadas = 0;
    private int ponchesLanzados = 0;
    private int juegosJugados = 0;
    private int basePorBola = 0;
    private int carrerasLimpiasPermitidas = 0;
    private float efectivas = 0.0f;

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

    public int getCarrerasLimpiasPermitidas() {
        return carrerasLimpiasPermitidas;
    }

    public void setCarrerasLimpiasPermitidas(int carrerasLimpiasPermitidas) {
        this.carrerasLimpiasPermitidas = carrerasLimpiasPermitidas;
    }

    public float getEfectivas() {
        return efectivas;
    }

    public void setEfectivas(float efectivas) {
        this.efectivas = efectivas;
    }

    //Metodos
    public float promedioPonches() {
        return (float) ponchesLanzados / entradasLanzadas;
    }

    public void incrementarEntradasLanzadas() {
        entradasLanzadas++;
    }

    public void incrementarBasesPorBola() {
        basePorBola++;
    }

    public void incrementarCarrerasLimpiasPermitidas() {
        carrerasLimpiasPermitidas++;
    }

    public void incrementarponchesLanzados() {
        ponchesLanzados++; //crear metodo para incrementar ponches lanzados
    }

    public Float calcularERA() {
        if (entradasLanzadas == 0) {
            // Evitamos la división por cero
            return 0f;
        }
        return (carrerasLimpiasPermitidas / (float) entradasLanzadas) * 9;
    }
}

