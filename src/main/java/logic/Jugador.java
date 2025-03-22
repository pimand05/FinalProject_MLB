package logic;

import logic.Lesion;

import java.time.LocalDate;
import java.util.ArrayList;

public class Jugador {
    protected String nombre;
    protected LocalDate fNacimiento;
    protected float altura;
    protected ArrayList<Lesion> historialLesiones;

    public Jugador(String nombre, LocalDate fNacimiento, float altura, ArrayList<Lesion> historialLesiones) {
        super();
        this.nombre = nombre;
        this.fNacimiento = fNacimiento;
        this.altura = altura;
        this.historialLesiones = historialLesiones != null ? historialLesiones : new ArrayList<>();
    }

    public String getNombre() {
            return nombre;
        }
    public LocalDate getfNacimiento() {
            return fNacimiento;
        }

    public float getAltura() {
            return altura;
        }

    public ArrayList<Lesion> getHistorialLesiones() {
            return historialLesiones;
        }

    // Método abstracto que debe implementar cada subclase
    public abstract void actualizarEstadisticas(Partido partido);
}

