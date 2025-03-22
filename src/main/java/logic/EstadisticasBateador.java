package logic;

public class EstadisticasBateador {
    private float promedioBateo;
    private int hits;
    private int homeRuns;
    private int carreras;
    private int juegosJugados;

    public EstadisticasBateador(float promedioBateo, int hits, int homeRuns, int carreras, int juegosJugados) {
        this.promedioBateo = promedioBateo;
        this.hits = hits;
        this.homeRuns = homeRuns;
        this.carreras = carreras;
        this.juegosJugados = juegosJugados;
    }

    public float getPromedioBateo() {
        return promedioBateo;
    }

    public void setPromedioBateo(float promedioBateo) {
        this.promedioBateo = promedioBateo;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getHomeRuns() {
        return homeRuns;
    }

    public void setHomeRuns(int homeRuns) {
        this.homeRuns = homeRuns;
    }

    public int getCarreras() {
        return carreras;
    }

    public void setCarreras(int carreras) {
        this.carreras = carreras;
    }

    public int getJuegosJugados() {
        return juegosJugados;
    }

    public void setJuegosJugados(int juegosJugados) {
        this.juegosJugados = juegosJugados;
    }

}
