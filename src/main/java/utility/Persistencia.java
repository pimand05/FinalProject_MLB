package utility;

import java.io.*;

public class Persistencia {
    // Guarda cualquier objeto serializable en la ruta especificada
    public static void guardarObjeto(Object objeto, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
            System.out.println("Datos guardados correctamente en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    // Carga un objeto desde la ruta especificada
    @SuppressWarnings("unchecked")
    public static <T> T cargarObjeto(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return null;
        }
    }
}
