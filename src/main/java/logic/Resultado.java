package logic;

public class Resultado {

    private int hitsLocal;
    private int hitsVisitante;
    private int carrerasLocal;
    private int carrerasVisitante;
    private int homeRunsLocal;
    private int homeRunsVisitante;

    public Resultado(int hitsLocal, int hitsVisitante, int carrerasLocal, int carrerasVisitante, int homeRunsLocal, int homeRunsVisitante) {
        this.hitsLocal = hitsLocal;
        this.hitsVisitante = hitsVisitante;
        this.carrerasLocal = carrerasLocal;
        this.carrerasVisitante = carrerasVisitante;
        this.homeRunsLocal = homeRunsLocal;
        this.homeRunsVisitante = homeRunsVisitante;
    }

    public int getHitsLocal() {
        return hitsLocal;
    }

    public void setHitsLocal(int hitsLocal) {
        this.hitsLocal = hitsLocal;
    }

    public int getHitsVisitante() {
        return hitsVisitante;
    }

    public void setHitsVisitante(int hitsVisitante) {
        this.hitsVisitante = hitsVisitante;
    }

    public int getCarrerasLocal() {
        return carrerasLocal;
    }

    public void setCarrerasLocal(int carrerasLocal) {
        this.carrerasLocal = carrerasLocal;
    }

    public int getCarrerasVisitante() {
        return carrerasVisitante;
    }

    public void setCarrerasVisitante(int carrerasVisitante) {
        this.carrerasVisitante = carrerasVisitante;
    }

    public int getHomeRunsLocal() {
        return homeRunsLocal;
    }

    public void setHomeRunsLocal(int homeRunsLocal) {
        this.homeRunsLocal = homeRunsLocal;
    }

    public int getHomeRunsVisitante() {
        return homeRunsVisitante;
    }

    public void setHomeRunsVisitante(int homeRunsVisitante) {
        this.homeRunsVisitante = homeRunsVisitante;
    }
}
