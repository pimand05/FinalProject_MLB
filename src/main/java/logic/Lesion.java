package logic;

import java.time.LocalDate;

public class Lesion {
    private String tipo;
    private LocalDate fecInicio;
    private LocalDate finEstimada;
    private int diasRecuperacion;

    public Lesion(String tipo, LocalDate fecInicio, LocalDate finEstimada, int diasRecuperacion) {
        super();
        this.tipo = tipo;
        this.fecInicio = fecInicio;
        this.finEstimada = finEstimada;
        this.diasRecuperacion = diasRecuperacion;
    }

    public LocalDate getFinEstimada() {
        return finEstimada;
    }

    public void setFinEstimada(LocalDate finEstimada) {
        this.finEstimada = finEstimada;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFecInicio() {
        return fecInicio;
    }

    public int getDiasRecuperacion() {
        return diasRecuperacion;
    }
}
