package logic;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public abstract class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected LocalDate fNacimiento;
    protected float altura;
    protected ArrayList<Lesion> historialLesiones;
    protected int numJugador;
    protected String imagenRoute;
    protected transient Image image;
    protected Lesion lesion;

    public Jugador(String nombre, LocalDate fNacimiento, float altura, int numJugador, String imagenRoute) {
        this.nombre = nombre;
        this.fNacimiento = fNacimiento;
        this.altura = altura;
        this.numJugador = numJugador;
        this.imagenRoute = imagenRoute;
        this.lesion = new Lesion(null);
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

    public Lesion getLesion() {
        return lesion;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    public Image getImagenRoute() {
        return image;
    }

    public void setImagenRoute(String imagenRoute) {
        this.imagenRoute = imagenRoute;
    }

    public void setImage(String imagenRoute) {
        this.image = new Image(imagenRoute);
    }

    // Método para calcular edad
    public int getEdad() {
        return Period.between(fNacimiento, LocalDate.now()).getYears();
    }

    // Método abstracto que debe implementar cada subclase
    public abstract void actualizarEstadisticas(Partido partido);
}

