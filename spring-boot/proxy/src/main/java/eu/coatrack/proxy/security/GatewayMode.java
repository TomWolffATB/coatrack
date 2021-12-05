package eu.coatrack.proxy.security;

/*
    If the gateway successfully receives the latest list of API keys from CoatRack admin, it goes to online mode.
    If a connection attempt to CoatRack admin server failed, it goes to the time-limited functioning offline mode.
 */

public enum GatewayMode {
    ONLINE, OFFLINE
}
