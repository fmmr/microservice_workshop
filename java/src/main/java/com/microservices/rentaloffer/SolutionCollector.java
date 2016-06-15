package com.microservices.rentaloffer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionCollector implements MessageHandler {


    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Connections connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionCollector());
    }

    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);
        if (!needPacket.hasNoSolutions()) {
            List<Solution> solutions = needPacket.getSolutions();
            Solution solution = getBest(solutions);

        }
    }

    private Solution getBest(List<Solution> solutions) {
        return solutions.get(0); // TODO implement
    }

}
