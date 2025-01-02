package server;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server starting...");

        // Start SOLO mode listener
        new Thread(() -> new SoloHandler().start()).start();

        // Start CROWD mode listener
        new Thread(() -> new CrowdHandler().start()).start();

        // Start LAN mode listener
        new Thread(() -> new LANHandler().start()).start();
    }
}

