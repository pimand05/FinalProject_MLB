package logic;

import logic.Lesion;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Jugador {
    protected String nombre;
    protected LocalDate fNacimiento;
    protected float altura;
    protected ArrayList<Lesion> historialLesiones;
    protected int numJugador;

    public Jugador(String nombre, LocalDate fNacimiento, float altura, int numJugador) {
        this.nombre = nombre;
        this.fNacimiento = fNacimiento;
        this.altura = altura;
        this.numJugador = numJugador;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setfNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    // Método abstracto que debe implementar cada subclase
    public abstract void actualizarEstadisticas(Partido partido);
}

