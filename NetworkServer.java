package com.hypernode.network;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.JSONObject;

/**
 * NetworkServer-Handles communication between hypernode nodes.
 */
public class NetworkServer {

    private int port;
    private Map<String, NodeConnection> connectedNodes;

    public NetworkServer(int port) {
        this.port = port;
        this.connectedNodes = new HashMap<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[INFO] NetworkServer started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO] New connection from " + clientSocket.getInetAddress());

                NodeConnection nodeConnection = new NodeConnection(clientSocket);
                new Thread(nodeConnection).start();
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Server error: " + e.getMessage());
        }
    }

    private class NodeConnection implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public NodeConnection(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Authenticate node
                String handshake = in.readLine();
                JSONObject handshakeJson = new JSONObject(handshake);
                String nodeId = handshakeJson.getString("nodeId");

                connectedNodes.put(nodeId, this);
                System.out.println("[INFO] Node connected: " + nodeId);

                // Listen for messages from the node
                String message;
                while ((message = in.readLine()) != null) {
                    handleMessage(nodeId, message);
                }
            } catch (Exception e) {
                System.err.println("[ERROR] Node connection error: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }

        private void handleMessage(String nodeId, String message) {
            System.out.println("[MESSAGE] From Node " + nodeId + ": " + message);
            // TODO: Process messages (e.g., task reports, heartbeats)
        }

        private void closeConnection() {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("[ERROR] Closing connection: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = 5000; // Replace with your preferred port
        NetworkServer server = new NetworkServer(port);
        server.start();
    }
}
