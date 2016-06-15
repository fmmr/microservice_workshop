package com.microservices.rentaloffer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SolutionProvider implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionProvider.class);
    private static Connections connection;

    public SolutionProvider(Connections connection) {
        this.connection = connection;
    }


    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);

        if (shouldProvideNewSolution(needPacket)) {
            needPacket.proposeSolution(new Solution(getType(), getValue(), getLikelyhood()));
            connection.publish(needPacket.toJson());
        }
    }

    abstract SolutionType getType();
    
    abstract double getLikelyhood();

    abstract int getValue();

    private boolean shouldProvideNewSolution(NeedPacket needPacket) {
        final List<Solution> solutions = needPacket.getSolutions();
        final boolean present = solutions.stream().filter(s -> s.getType() == getType()).findFirst().isPresent();
        return !present;
    }

}
