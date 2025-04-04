package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class PersistenciaJSON {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Adaptador de fechas
            .registerTypeAdapterFactory(AdaptadorJugador.get())    // Adaptador de jerarquía Jugador
            .setPrettyPrinting()
            .create();

    // Guarda un objeto como JSON
    public static void guardarJson(Object objeto, String ruta) {
        try (Writer writer = new FileWriter(ruta)) {
            gson.toJson(objeto, writer);
            System.out.println("Datos guardados correctamente en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    // Carga un objeto desde JSON
    public static <T> T cargarJson(String ruta, Class<T> clase) {
        try (Reader reader = new FileReader(ruta)) {
            return gson.fromJson(reader, clase);
        } catch (IOException e) {
            System.out.println("Error al cargar JSON: " + e.getMessage());
            return null;
        }
    }
}
