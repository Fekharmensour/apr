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
        Scanner scanner = new Scanner(System.in);

        // Display game mode selection menu
        System.out.println("Welcome to the Game Server!");
        System.out.println("Please choose a game mode:");
        System.out.println("1. SOLO ");
        System.out.println("2. CROWD");
        System.out.println("3. LAN");

        int choice = 0;
        int port = 0;

        // Validate user input
        while (true) {
            System.out.print("Enter your choice (1/2/3): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) {
                    port = Config.SERVER_PORT;
                    break;
                } else if (choice == 2) {
                    port = Config.SERVER_PORT_CROWD;
                    break;
                } else if (choice == 3) {
                    port = 5003;
                    break;
                } else {
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        try (
                // Establish connection to the selected port
                Socket socket = new Socket("localhost", port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            Logger.log("Connected to the server on port " + port);

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
