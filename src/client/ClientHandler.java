package client;

import utils.Logger;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Welcome to the server!");
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                Logger.log("Received: " + clientMessage);
                if ("exit".equalsIgnoreCase(clientMessage)) {
                    out.println("Goodbye!");
                    break;
                }
                out.println("Echo: " + clientMessage);
            }
        } catch (IOException e) {
            Logger.log("Client disconnected: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Logger.log("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

