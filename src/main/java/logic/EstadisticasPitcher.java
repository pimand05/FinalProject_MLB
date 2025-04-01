package logic;

public class EstadisticasPitcher {
    private int entradasLanzadas;
    private int ponchesLanzados;
    private int juegosJugados;
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

    //Metodos
    public float promedioPonches() {
        return (float) ponchesLanzados / entradasLanzadas;
    }

    public void incrementarponchesLanzados() {
        ponchesLanzados++; //crear metodo para incrementar ponches lanzados
    }

    public double calcularERA() {
        return (carrerasLimpiasPermitidas / (double) entradasLanzadas) * 9;
    }
}

