import java.io.*;
import java.net.*;
import java.util.*;

/**
 * PROJECT: Multithreaded Chat Application
 * TASK 3: Server-Client Communication using Threads
 * USAGE: 
 * 1. Compile: javac ChatSystem.java
 * 2. Run Server: java ChatSystem server
 * 3. Run Client: java ChatSystem client
 */
public class ChatSystem {
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java ChatSystem [server|client]");
            return;
        }

        if (args[0].equalsIgnoreCase("server")) {
            startServer();
        } else {
            startClient();
        }
    }

    // --- SERVER LOGIC ---
    public static void startServer() throws Exception {
        System.out.println("--- Ayush's Chat Server Started (Port 5000) ---");
        ServerSocket serverSocket = new ServerSocket(5000);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        public Handler(Socket socket) { this.socket = socket; }
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                
                synchronized (clientWriters) { clientWriters.add(out); }
                System.out.println("New User Connected! Total: " + clientWriters.size());

                String msg;
                while ((msg = in.readLine()) != null) {
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) writer.println(msg);
                    }
                }
            } catch (IOException e) { System.out.println("User disconnected."); }
        }
    }

    // --- CLIENT LOGIC ---
    public static void startClient() throws Exception {
        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to Server! Type messages below:");

        // Thread to Receive
        new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String reply;
                while ((reply = in.readLine()) != null) System.out.println("\n[Broadcast]: " + reply);
            } catch (IOException e) { }
        }).start();

        // Main Thread to Send
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            out.println(sc.nextLine());
        }
    }
}
