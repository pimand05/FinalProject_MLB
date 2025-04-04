package utility;

import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import logic.Bateador;
import logic.Jugador;
import logic.Pitcher;

public class AdaptadorJugador {

    public static RuntimeTypeAdapterFactory<Jugador> get() {
        return RuntimeTypeAdapterFactory
                .of(Jugador.class, "tipo")
                .registerSubtype(Bateador.class, "Bateador")
                .registerSubtype(Pitcher.class, "Pitcher");
    }
}
