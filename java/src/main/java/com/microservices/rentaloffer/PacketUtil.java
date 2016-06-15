package com.microservices.rentaloffer;

public final class PacketUtil {
    private PacketUtil() {
    }

    public static boolean isPingPacket(String json) {
        return json.contains("PingPacket");
    }
    public static boolean isNeedPacket(String json) {
        return json.contains("NeedPacket");
    }
}
