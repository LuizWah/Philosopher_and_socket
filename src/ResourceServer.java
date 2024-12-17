package src;


import java.io.*;
import java.net.*;
import java.util.*;

public class ResourceServer {
    private static final int PORT = 5000;
    public Map<Integer, philosopherDados> philosophers = new HashMap<>();
    private Mordomo mordomo1 = new Mordomo();
    
    public static final int NUMBER_FORKS = 10;
    public final int NUMBER_PHILOSOPHERS = 10;

    public ResourceServer(int numForks) {
        mordomo1.forks = new boolean[numForks]; 
    }

    public static void main(String[] args) {
        ResourceServer server = new ResourceServer(NUMBER_FORKS); 
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);
            int id =1;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new PhilosopherHandler(clientSocket, philosophers.get(id), mordomo1, this)).start();
                id +=1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}    