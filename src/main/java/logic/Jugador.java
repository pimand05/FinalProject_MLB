package logic;

import com.sun.javafx.scene.control.GlobalMenuAdapter;
import javafx.scene.image.Image;
import utility.LesionTipo;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;

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
    protected transient String Equipo;

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

    public String getImagenRoute() {
        return imagenRoute;
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

    public void setHistorialLesiones(ArrayList<Lesion> historialLesiones) {
        this.historialLesiones = historialLesiones;
    }

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public void setLesion(Lesion lesion) {
        this.lesion = lesion;
    }

    // Método para calcular edad
    public int getEdad() {
        return Period.between(fNacimiento, LocalDate.now()).getYears();
    }

    // Método abstracto que debe implementar cada subclase
    public abstract void actualizarEstadisticas(Partido partido);

    public Image getfoto() {
        File archivo = new File(imagenRoute);
        if (archivo.exists()) {
            foto = new Image(archivo.toURI().toString());
        }
        return foto;
    }

    public void loadImageJugador() {
        File archivo = new File(imagenRoute);
        if (archivo.exists()) {
            foto = new Image(archivo.toURI().toString());
        }
    }
}

