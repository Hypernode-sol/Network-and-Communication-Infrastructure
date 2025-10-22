package com.hypernode.network;

import org.json.JSONObject;

/**
 * MessageHandler - Processes incoming messages for the hypernode network.
 */
public class MessageHandler {

    public enum MessageType {
        HEARTBEAT, TASK_RESULT, STATUS_UPDATE, ERROR
    }

    /**
     * Processes a message received by the server.
     * 
     * @param nodeId   The ID of the node sending the message.
     * @param message  The message content in JSON format.
     */
    public void handleMessage(String nodeId, String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String type = jsonMessage.getString("type");

            switch (MessageType.valueOf(type)) {
                case HEARTBEAT:
                    handleHeartbeat(nodeId, jsonMessage);
                    break;
                case TASK_RESULT:
                    handleTaskResult(nodeId, jsonMessage);
                    break;
                case STATUS_UPDATE:
                    handleStatusUpdate(nodeId, jsonMessage);
                    break;
                case ERROR:
                    handleError(nodeId, jsonMessage);
                    break;
                default:
                    System.err.println("[ERROR] Unknown message type: " + type);
            }
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to process message from node " + nodeId + ": " + e.getMessage());
        }
    }

    private void handleHeartbeat(String nodeId, JSONObject message) {
        System.out.println("[HEARTBEAT] Received from node " + nodeId + ": " + message);
        // Update node's last active timestamp
    }

    private void handleTaskResult(String nodeId, JSONObject message) {
        System.out.println("[TASK_RESULT] Received from node " + nodeId + ": " + message);
        // Process task results
    }

    private void handleStatusUpdate(String nodeId, JSONObject message) {
        System.out.println("[STATUS_UPDATE] Received from node " + nodeId + ": " + message);
        // Update network status
    }

    private void handleError(String nodeId, JSONObject message) {
        System.err.println("[ERROR] Reported by node " + nodeId + ": " + message);
        // Log and respond to errors
    }
}
