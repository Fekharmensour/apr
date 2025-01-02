package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * LANHandler.java - Handles broadcasting questions over LAN.
 */
public class LANHandler {

    private static final String GROUP_ADDRESS = "231.110.75.40";
    private static final int PORT = 6000;

    /**
     * Starts the LAN broadcast to send a question to connected clients.
     */
    public void start() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);

            String message = "LAN Question: What is 2+2? Options: A)3, B)4, C)5";
            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);

            System.out.println("✅ LAN Question sent successfully to " + GROUP_ADDRESS + ":" + PORT);

        } catch (IOException e) {
            System.err.println("❌ Error while sending LAN question: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
