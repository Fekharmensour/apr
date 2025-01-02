package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * LANClient.java - Handles communication in LAN mode using multicast.
 */
public class LANClient {
    private static final String MULTICAST_GROUP = "231.110.75.40";
    private static final int PORT = 6000;

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
            socket.joinGroup(group);

            System.out.println("Joined multicast group: " + MULTICAST_GROUP + " on port " + PORT);

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // Listen for a message
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received: " + message);

            socket.leaveGroup(group);
            System.out.println("Left multicast group: " + MULTICAST_GROUP);

        } catch (IOException e) {
            System.err.println("Error in LANClient: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
