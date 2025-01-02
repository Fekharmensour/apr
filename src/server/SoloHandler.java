package server;

import java.io.*;
import java.net.*;
import java.util.List;

/**
 * SoloHandler.java - Handles SOLO mode (one player answering one question at a time).
 */
public class SoloHandler {

    private static final int PORT = 5000;

    /**
     * Starts the SOLO mode server and listens for client connections.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("SOLO Mode active on port " + PORT + "...");
            while (true) {
                // Accept incoming client connections and start a new thread for each
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("❌ Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles a single client's question-answering process.
     *
     * @param socket The socket for the connected client.
     */
    private void handleClient(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Load questions from the file and select the first question for SOLO mode
            List<Question> questions = QuestionLoader.loadQuestions("resources/questions.txt");

            if (questions.isEmpty()) {
                out.println("❌ No questions available.");
                socket.close();
                return;
            }

            Question question = questions.get(0); // Taking the first question for this example
            out.println("Question: " + question.getQuestion());
            out.println("Options: " + String.join(", ", question.getOptions()));

            // Get the client's answer and check if it's correct
            String answer = in.readLine();
            if (question.isCorrect(answer)) {
                out.println("Correct! You win!");
            } else {
                out.println("Wrong answer. Try again!");
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("❌ Error handling client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
