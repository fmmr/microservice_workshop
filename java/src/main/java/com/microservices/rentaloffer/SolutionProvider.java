package com.microservices.rentaloffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionProvider implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionProvider.class);
    private static Connections connection;

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionProvider());
    }

    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);

        if (needPacket.hasNoSolutions()) {
            needPacket.proposeSolution(new Solution(100, 0.5d));
            connection.publish(needPacket.toJson());
        }
    }

}
