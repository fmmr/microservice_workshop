package com.microservices.rentaloffer;

public class Ping implements Client {

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Ping ping = new Ping();
        ping.publish(host, port);
    }

    public void publish(String host, String port) {
        try (Connections connection = new Connections(host, port)) {
            while (true) {
                PingPacket pingPacket = new PingPacket();
                connection.publish(pingPacket.toJson(sign()));
                Thread.sleep(15000);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not publish ping:", e);
        }
    }

    @Override
    public String sign() {
        return "Ping";
    }
}
