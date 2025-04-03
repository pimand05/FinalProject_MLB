package logic;

import java.io.Serializable;

public class EstadisticasBateador implements Serializable  {
    private static final long serialVersionUID = 1L;
    private float promedioBateo;
    private int hits = 0;
    private int homeRuns = 0;
    private int carreras = 0;
    private int juegosJugados = 0;

    //Constructor
    public EstadisticasBateador(float promedioBateo, int hits, int homeRuns, int carreras, int juegosJugados) {
        this.promedioBateo = promedioBateo;
        this.hits = hits;
        this.homeRuns = homeRuns;
        this.carreras = carreras;
        this.juegosJugados = juegosJugados;
    }

    //Setters y Getters
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

    //Metodos
    public void incrementarHits() {
        hits++;
    }

    public void incrementarHomeRuns() {
        homeRuns++;
    }

    public void incrementarCarreras() {
        carreras++;
    }

    public void incrementarJuegosJugados() {
        juegosJugados++;
    }

    public void calcularPromedioBateo() {
        promedioBateo = hits / juegosJugados;
    }

}
