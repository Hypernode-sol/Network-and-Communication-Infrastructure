package com.hypernode.network;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NodeRegistry-Manages connected nodes in the hypernode network.
 */
public class NodeRegistry {

    private final Map<String, NodeInfo> nodes;

    public NodeRegistry() {
        this.nodes = new ConcurrentHashMap<>();
    }

    public void registerNode(String nodeId, String ipAddress, int capacity) {
        nodes.put(nodeId, new NodeInfo(nodeId, ipAddress, capacity));
        System.out.println("[INFO] Node registered: " + nodeId);
    }

    public void removeNode(String nodeId) {
        nodes.remove(nodeId);
        System.out.println("[INFO] Node removed: " + nodeId);
    }

    public NodeInfo getNodeInfo(String nodeId) {
        return nodes.get(nodeId);
    }

    public List<NodeInfo> getAllNodes() {
        return new ArrayList<>(nodes.values());
    }

    public static class NodeInfo {
        public String nodeId;
        public String ipAddress;
        public int capacity;

        public NodeInfo(String nodeId, String ipAddress, int capacity) {
            this.nodeId = nodeId;
            this.ipAddress = ipAddress;
            this.capacity = capacity;
        }

        @Override
        public String toString() {
            return String.format("Node[id=%s, ip=%s, capacity=%d]", nodeId, ipAddress, capacity);
        }
    }
}
