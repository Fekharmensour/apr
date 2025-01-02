package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import utils.Logger;

/**
 * CrowdHandler.java - Manages multiple clients in CROWD mode.
 */
public class CrowdHandler {
    private static final int PORT = 5001;
    private static final ConcurrentMap<String, Socket> clients = new ConcurrentHashMap<>();
    private static final List<Question> questions = QuestionLoader.loadQuestions("resources/questions.txt");

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Logger.log("CROWD Mode active on port " + PORT + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Logger.log("New client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            Logger.log("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        String clientId = UUID.randomUUID().toString();
        clients.put(clientId, socket);
        Logger.log("Client assigned ID: " + clientId);

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Iterate through questions
            for (Question question : questions) {
                out.println("Question: " + question.getQuestion());
                out.println("Options: " + String.join(", ", question.getOptions()));

                String answer = in.readLine();
                if (question.isCorrect(answer)) {
                    out.println("Correct Answer!");
                } else {
                    out.println("Wrong Answer! The correct answer was: " + question.getCorrectAnswer());
                }
            }

            out.println("Game Over. Thanks for playing!");
        } catch (IOException e) {
            Logger.log("Client " + clientId + " disconnected unexpectedly.");
        } finally {
            clients.remove(clientId);
            try {
                socket.close();
            } catch (IOException e) {
                Logger.log("Failed to close socket for client " + clientId);
            }
            Logger.log("Client " + clientId + " connection closed.");
        }
    }
}
