package com.microservices.rentaloffer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microservices.rentaloffer.PacketUtil.isPingPacket;

public class SolutionCollector implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionCollector.class);
    NumberFormat formatter = new DecimalFormat("#0.00");

    Map<String, Solution> bestSolutions = new HashMap<>();
    private static Connections connection;

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionCollector());
    }

    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);
        if (isPingPacket(message)) {
            final PingPacket pingPacket = PingPacket.fromJson(message);
            pingPacket.increaseReadCount();
            connection.publish(pingPacket.toJson(sign()));
        } else {
            if (needPacket.hasSolutions()) {
                List<Solution> solutions = needPacket.getSolutions();

                String needId = needPacket.getId();
                final Solution sol = bestSolutions.get(needId);

                List<Solution> tmpSol = new ArrayList<>(solutions);
                if (sol != null) {
                    tmpSol.add(sol);
                }

                Solution solution = getBest(tmpSol);
                bestSolutions.put(needId, solution);

                double score = solution.getValue() * solution.getLikelyhood();

                final String scoreInString = formatter.format(score);
                final String collect = tmpSol.stream()
                                             .map(s -> s.getLikelyhood() * s.getValue())
                                             .map(formatter::format)
                                             .collect(Collectors.joining(", "));
                logger.info("BEST of (" + solutions.size() + "), score: " + scoreInString + " of [" + collect + "]: " + solution + ", id: " + needId);

            }
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

    @Override
    public String sign() {
        return "solcol";
    }
}
