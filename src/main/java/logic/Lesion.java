package logic;

import utility.LesionTipo;

import java.io.Serializable;
import java.time.LocalDate;

public class Lesion implements Serializable {
    private LesionTipo tipo;
    private LocalDate fecInicio;
    private LocalDate finEstimada;
    private int diasRecuperacion;
    private boolean activa;

    public Lesion(LesionTipo tipo) {
        super();
        this.tipo = LesionTipo.NINGUNA;
        this.fecInicio = LocalDate.now();
        this.finEstimada = fecInicio.plusDays(diasRecuperacion);
        this.diasRecuperacion = calcularDiasRecuperacion();
        this.activa = false;
    }

    public LocalDate getFinEstimada() {
        return finEstimada;
    }

    public void setFinEstimada(LocalDate finEstimada) {
        this.finEstimada = finEstimada;
    }

    public LesionTipo getTipo() {
        return tipo;
    }

    public LocalDate getFecInicio() {
        return fecInicio;
    }

    public int getDiasRecuperacion() {
        return diasRecuperacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void marcarComoCurada() {
        this.activa = false;
    }

    private int calcularDiasRecuperacion() {
        // Variación de +/- 10% del tiempo base
        int variacion = (int)(tipo.getDiasRecuperacionBase() * 0.1 * (Math.random() > 0.5 ? 1 : -1));
        return Math.max(1, tipo.getDiasRecuperacionBase() + variacion); // Mínimo 1 día
    }

}
