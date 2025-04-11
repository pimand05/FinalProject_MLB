package logic;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerJSON {
    public static void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor: Esperando conexiones...");

            // Loop infinito para aceptar múltiples conexiones
            while (true) {
                Socket socket = serverSocket.accept(); // Acepta una conexión
                System.out.println("Servidor: Cliente conectado desde " + socket.getInetAddress());
                new Thread(new ConnectionHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static class ConnectionHandler implements Runnable {
        private static int contador = 1;
        private Socket socket;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream dis = new DataInputStream(socket.getInputStream())) {
                // Leer el prefijo de longitud del JSON
                int longitud = dis.readInt();
                byte[] jsonBytes = new byte[longitud];
                dis.readFully(jsonBytes);
                String json = new String(jsonBytes, "UTF-8");

                try (FileWriter fw = new FileWriter("backUp"+contador+".json")) {
                    fw.write(json);
                    contador++;
                }
                System.out.println("Cliente " + socket.getInetAddress() + " envió: " + json);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
