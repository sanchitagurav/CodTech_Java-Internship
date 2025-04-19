import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(10); // Thread pool to handle clients

    public static void main(String[] args) {
        System.out.println("Chat Server Started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept new client
                pool.execute(new ClientHandler(clientSocket)); // Submit task to the thread pool
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler is now responsible for handling client communication
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Set up input/output streams for communication with the client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Add the client to the writers set
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Broadcast incoming messages to all clients
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message received: " + message);
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    synchronized (clientWriters) {
                        clientWriters.remove(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
