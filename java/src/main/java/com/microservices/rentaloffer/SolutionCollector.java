package com.microservices.rentaloffer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionCollector implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionCollector.class);

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Connections connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionCollector());
    }

    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);
        if (needPacket.hasSolutions()) {
            List<Solution> solutions = needPacket.getSolutions();
            Solution solution = getBest(solutions);
            double score = solution.getValue() * solution.getLikelyhood();
            logger.info("BEST of (" + solutions.size() + "), score: " + score + ": " + solution.toJson());

        }
    }

    private Solution getBest(List<Solution> solutions) {
        double highestScore = 0;
        Solution bestSolution = null;

        for (Solution solution : solutions) {
            double score = solution.getValue() * solution.getLikelyhood();
            if (score > highestScore) {
                highestScore = score;
                bestSolution = solution;
            }
        }

        return bestSolution;

    }

}
