package utility;

public enum COMENTARIOS {
    HIT("ha conectado un hit"),
    HOMERUN("ha conectado un cuadrangular"),
    PONCHE("se ha ponchado"),
    BASExBOLA("ha recibido una base por bola"),
    STRIKE("strike"),
    BOLA("bola");

    private final String mensaje;

    // Constructor
    COMENTARIOS(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getter
    public String getMensaje() {
        return mensaje;
    }
}