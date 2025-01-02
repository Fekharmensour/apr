package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import utils.Config;
import utils.Logger;

/**
 * Client.java - Main entry point for the client application.
 */
public class Client {
    public static void main(String[] args) {
        try (
                // Establish connection to the server
                Socket socket = new Socket(Config.SERVER_HOST, Config.SERVER_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            Logger.log("Connected to the server at " + Config.SERVER_HOST + ":" + Config.SERVER_PORT);

            // Read welcome message from the server
            System.out.println(in.readLine());

            // Main client loop
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage); // Display server messages (e.g., Question, Options)

                if (serverMessage.equalsIgnoreCase("Goodbye!")) {
                    Logger.log("Server closed the connection.");
                    break;
                }

                // Read user input and send to server
                System.out.print("Your Answer: ");
                String userInput = scanner.nextLine();
                out.println(userInput);

                // Read server response (Correct/Incorrect, next instructions)
                String response = in.readLine();
                System.out.println(response);

                if (response.equalsIgnoreCase("Goodbye!")) {
                    Logger.log("Game ended by server.");
                    break;
                }
            }

        } catch (IOException e) {
            Logger.log("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
