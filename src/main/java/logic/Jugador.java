package logic;

import javafx.scene.image.Image;
import utility.LesionTipo;

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
    protected transient Image foto;
    protected Lesion lesion;
    protected String Equipo;

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
        return foto;
    }

    public void setImagenRoute(String imagenRoute) {
        this.imagenRoute = imagenRoute;
    }

    public void setImage(String imagenRoute) {
        this.foto = new Image(imagenRoute);
    }



    public String getEquipo() {
        return Equipo;
    }

    public void setEquipo(String equipo) {
        this.Equipo = equipo;
    }



    // Metodos para manejar las lesiones
    public boolean puedeJugar() {
        return lesion == null || !lesion.isActiva();
    }

    public void aplicarLesion(LesionTipo tipo) {
        this.lesion = new Lesion(tipo);
    }


    public void curarLesion() {
        if (lesion != null) {
            lesion.marcarComoCurada();
        }
    }

    // Método para calcular edad
    public int getEdad() {
        return Period.between(fNacimiento, LocalDate.now()).getYears();
    }

    // Método abstracto que debe implementar cada subclase
    public abstract void actualizarEstadisticas(Partido partido);

    public Image getfoto() {
        this.foto = new Image(imagenRoute);
        return foto;
    }
}

