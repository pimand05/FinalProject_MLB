package logic;

public class EstadisticasPitcher {
    private int entradasLanzadas;
    private int ponchesLanzados;
    private int juegosJugados;

    public EstadisticasPitcher(int entradasLanzadas, int ponchesLanzados, int juegosJugados) {
        this.entradasLanzadas = entradasLanzadas;
        this.ponchesLanzados = ponchesLanzados;
        this.juegosJugados = juegosJugados;
    }

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

}

