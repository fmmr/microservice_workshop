package com.microservices.rentaloffer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Need implements Client {

    private static List<String> userNames = Arrays.asList("Lotte", "Henning", "Nicolai", "Fredrik", null);

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Need need = new Need();
        need.publish(host, port);
    }

    public void publish(String host, String port) {
        try (Connections connection = new Connections(host, port)) {
            while (true) {
                int rnd = new Random().nextInt(userNames.size());
                NeedPacket needPacket = new NeedPacket();
                needPacket.setUserid(userNames.get(rnd));
                connection.publish(needPacket.toJson(sign()));
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not publish message:", e);
        }
    }

    @Override
    public String sign() {
        return "Need";
    }
}
