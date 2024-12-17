package src;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PhilosopherClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    private String Name;
    private AtomicInteger messageIdCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        PhilosopherClient client = new PhilosopherClient();
        client.start();
    }

    public void start() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado ao servidor\nLOGIN: ");
            login(out, in);
            Random random = new Random();

            while (true) {
                think(out, in, random);
                requestForks(out, in);
                eat(out, in, random);
                releaseForks(out, in);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro durante a execução do cliente: " + e.getMessage());
        }
    }

    private void login(PrintWriter out, BufferedReader in) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Name = scanner.nextLine();
        scanner.close();


        int id = messageIdCounter.incrementAndGet();
        out.println(id + " LOGIN: " + Name);
        String response = in.readLine();
        validateResponse(id, response);
        System.out.println("Login: " + response);
    }

    private void think(PrintWriter out, BufferedReader in, Random random) throws InterruptedException, IOException {
        int id = messageIdCounter.incrementAndGet();
        int thinkingTime = (int) Math.max(5 + random.nextGaussian() * 2, 0);

        out.println(id + " THINK");
        String response = in.readLine();
        validateResponse(id, response);
        System.out.println("Filósofo " + Name + " pensando por " + thinkingTime + "ms");
        Thread.sleep(thinkingTime);
    }

    private void requestForks(PrintWriter out, BufferedReader in) throws IOException, InterruptedException {
        int id = messageIdCounter.incrementAndGet();
        out.println(id + " REQUEST_FORKS");
        String response = in.readLine();


        while(response.contains("Não")){
            id = messageIdCounter.incrementAndGet();
            out.println(id + " REQUEST_FORKS");
            response = in.readLine();
            validateResponse(id, response);
            if(!response.contains("Não")){
                break;
            }
            System.out.println("Filósofo " + Name + response);
            Thread.sleep(1000);
        }

        System.out.println("Filósofo " + Name + response);
    }

    private void eat(PrintWriter out, BufferedReader in, Random random) throws InterruptedException, IOException {
        int id = messageIdCounter.incrementAndGet();
        int eatingTime = 2;

        out.println(id + " EAT");
        String response = in.readLine();
        validateResponse(id, response);
        System.out.println("Filósofo " + Name + " comendo por " + eatingTime + "ms");
        Thread.sleep(eatingTime);
    }

    private void releaseForks(PrintWriter out, BufferedReader in) throws IOException {
        int id = messageIdCounter.incrementAndGet();
        out.println(id + " RELEASE_FORKS");
        String response = in.readLine();
        validateResponse(id, response);
        System.out.println("Filósofo " + Name + " liberou os garfos.");
    }

    private void validateResponse(int id, String response) {
        String expectedPrefix = id + " ";
        if (response == null) {
            throw new IllegalStateException("Nenhuma resposta do servidor. O servidor pode ter desconectado.");
        }
        else if (!response.startsWith(expectedPrefix)) {
            throw new IllegalStateException("Resposta inesperada: " + response);
        }
    }
}
