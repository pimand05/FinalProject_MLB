package utility;

public enum COMENTARIOS {
    HIT("ha conectado un hit"),
    HOMERUN("ha conectado un cuadrangular!!!"),
    PONCHE("out! se ha ponchado"),
    BASExBOLA("ha recibido una base por bola"),
    STRIKE("strike"),
    BOLA("bola"),
    FLY("out! ha conectado un fly");

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