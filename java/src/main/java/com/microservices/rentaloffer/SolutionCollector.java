package com.microservices.rentaloffer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionCollector implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionCollector.class);
    NumberFormat formatter = new DecimalFormat("#0.00");

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


            final String scoreInString = formatter.format(score);
            final String collect = solutions.stream()
                                            .map(s -> s.getLikelyhood() * s.getValue())
                                            .map(formatter::format)
                                            .collect(Collectors.joining(", "));
            logger.info("BEST of (" + solutions.size() + "), score: " + scoreInString + " of [" + collect + "]: " + solution.toJson());

        }
    }

    private Solution getBest(List<Solution> solutions) {
        return solutions.stream()
                .max(this::bestComparator)
                .orElseThrow(() -> new RuntimeException("Should be a solution"));
    }

    private int bestComparator(Solution solution, Solution solution1) {
        double score = solution.getLikelyhood() * solution.getValue();
        double score1 = solution1.getLikelyhood() * solution1.getValue();
        return Double.compare(score, score1);
    }

}
