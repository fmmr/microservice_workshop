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
            Optional<Level> level = Optional.ofNullable(needPacket.getLevel());
            needPacket.proposeSolution(new Solution(getType(), getValue(level), getLikelyhood(level)));
            connection.publish(needPacket.toJson());
        }
    }

    abstract SolutionType getType();
    
    abstract double getLikelyhood(Optional<Level> level);

    abstract int getValue(Optional<Level> level);

    private boolean shouldProvideNewSolution(NeedPacket needPacket) {
        return needPacket.getSolutions().size() == 0;
    }

}
