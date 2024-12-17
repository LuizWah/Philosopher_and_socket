package src;
import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhilosopherHandler implements Runnable {
    private Socket clientSocket;
    private int philosopherId;
    private philosopherDados philosopher;
    private Mordomo mordomo1;
    private ResourceServer server;

    public PhilosopherHandler(Socket clientSocket, philosopherDados philosopher, Mordomo mordomo1, ResourceServer server) {
        this.clientSocket = clientSocket;
        this.philosopher = philosopher;
        this.mordomo1 = mordomo1;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                String response = handleRequest(message);
                if (response != null) {
                    out.println(response);
                }
            }
        } catch (IOException e) {
            server.philosophers.get(philosopherId).ChangeState("DESCONECTADO");
            e.printStackTrace();
        }
    }

    private synchronized String handleRequest(String message) {
        String[] parts = message.split(" ", 2);
        String login_name = "";
        String id = parts[0];
        String command = parts[1];
        
        if(message.contains("LOGIN")){
            String[] parts_command = command.split(" ", 2);
            login_name = command;
            command = parts_command[0];
        }

        switch (command) {
            case "LOGIN:":
                return handleLogin(id, login_name);
            case "THINK":
                server.philosophers.get(philosopherId).incrementThinking(philosopherId);
                return id + " PENSANDO";
            case "REQUEST_FORKS":
                if (mordomo1.tryAllocateForks(philosopherId)) {
                    return id + " Pegou forks";
                } else {
                    return id + " Não pegou forks";
                }
            case "RELEASE_FORKS":
                mordomo1.releaseForks(philosopherId);
                return id + " Soltou os garfos";
            case "EAT":
                server.philosophers.get(philosopherId).incrementEating(philosopherId);
                return id + " COMEU";
            default:
                return id + " Comando desconhecido";
        }
    }

    private String handleLogin(String id, String command) {
        String loginRegex = "LOGIN: [a-zA-Z0-9]+";
        Pattern pattern = Pattern.compile(loginRegex);
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String matchedString = matcher.group();
            if (matchedString.length() > 7) {
                String nome = matchedString.substring(7);

                for (int j = 1; j <= server.philosophers.size(); j++) {
                    if (server.philosophers.get(j).Name.equals(nome)) {
                        if (server.philosophers.get(j).Status.equals("DESCONECTADO")) {
                            server.philosophers.get(j).ChangeState("CONECTADO");
                            philosopherId = j;
                            return id + " Reconectado como " + nome;
                        } else {
                            return id + " Esse filósofo já está logado";
                        }
                    }
                }

                if (server.philosophers.size() < server.NUMBER_PHILOSOPHERS) {
                    philosopherId = server.philosophers.size() + 1;
                    server.philosophers.put(philosopherId, new philosopherDados(nome, "CONECTADO"));
                    return id + " Filósofo criado e conectado";
                } else {
                    return id + " Número máximo de filósofos alcançado";
                }
            }
        }
        return id + " Comando inválido";
    }
}
