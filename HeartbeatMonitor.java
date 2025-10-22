package com.hypernode.network;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HeartbeatMonitor - Monitors node heartbeats and identifies inactive nodes.
 */
public class HeartbeatMonitor {

    private final Map<String, Long> nodeLastSeen;
    private final long timeoutThreshold; // In milliseconds

    public HeartbeatMonitor(long timeoutThreshold) {
        this.nodeLastSeen = new ConcurrentHashMap<>();
        this.timeoutThreshold = timeoutThreshold;
    }

    /**
     * Updates the last seen timestamp for a node.
     * 
     * @param nodeId The ID of the node.
     */
    public void updateHeartbeat(String nodeId) {
        nodeLastSeen.put(nodeId, System.currentTimeMillis());
    }

    /**
     * Checks for nodes that have timed out and returns their IDs.
     * 
     * @return List of inactive node IDs.
     */
    public List<String> checkForInactiveNodes() {
        List<String> inactiveNodes = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for (Map.Entry<String, Long> entry : nodeLastSeen.entrySet()) {
            if (currentTime - entry.getValue() > timeoutThreshold) {
                inactiveNodes.add(entry.getKey());
            }
        }

        return inactiveNodes;
    }
}
