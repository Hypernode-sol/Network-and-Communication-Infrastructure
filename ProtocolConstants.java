package com.hypernode.network;

/**
 * ProtocolConstants - Defines protocol constants for hypernode communication.
 */
public class ProtocolConstants {

    // Message types
    public static final String MESSAGE_TYPE_HEARTBEAT = "HEARTBEAT";
    public static final String MESSAGE_TYPE_TASK_RESULT = "TASK_RESULT";
    public static final String MESSAGE_TYPE_STATUS_UPDATE = "STATUS_UPDATE";
    public static final String MESSAGE_TYPE_ERROR = "ERROR";

    // Heartbeat interval
    public static final long HEARTBEAT_INTERVAL = 5000; // In milliseconds

    // Timeout threshold
    public static final long NODE_TIMEOUT_THRESHOLD = 15000; // In milliseconds
}
