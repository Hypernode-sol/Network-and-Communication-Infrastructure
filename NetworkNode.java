package com.hypernode.network;

import java.io.*;
import java.net.*;
import org.json.JSONObject;

/**
 * NetworkNode - Represents a hypernode node connecting to the network server.
 */
public class NetworkNode {

    private String nodeId;
    private String serverHost;
    private int serverPort;

    public NetworkNode(String nodeId, String serverHost, int serverPort) {
        this.nodeId = nodeId;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("[INFO] Connected to NetworkServer at " + serverHost + ":" + serverPort);

            // Send handshake ...
            JSONObject handshake = new JSONObject();
            handshake.put("nodeId", nodeId);
            out.println(handshake.toString());

            // Example: Send periodic heartbeats
            new Thread(() -> {
                try {
                    while (true) {
                        JSONObject heartbeat = new JSONObject();
                        heartbeat.put("type", "HEARTBEAT");
                        heartbeat.put("timestamp", System.currentTimeMillis());
                        out.println(heartbeat.toString());

                        Thread.sleep(5000); // Heartbeat interval
                    }
                } catch (Exception e) {
                    System.err.println("[ERROR] Heartbeat error: " + e.getMessage());
                }
            }).start();

            // Listen for messages from the server
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[MESSAGE] From Server: " + message);
            }

        } catch (IOException e) {
            System.err.println("[ERROR] Node error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String nodeId = "Node1";
        String serverHost = "localhost";
        int serverPort = 5000;

        NetworkNode node = new NetworkNode(nodeId, serverHost, serverPort);
        node.start();
    }
}
