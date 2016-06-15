package com.microservices.rentaloffer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionProviderB extends SolutionProvider {

    protected static Logger logger = LoggerFactory.getLogger(SolutionProviderB.class);

    public SolutionProviderB(Connections connection) {
        super(connection);
    }

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Connections connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionProviderB(connection));
    }


    @Override
    SolutionType getType() {
        return SolutionType.B;
    }

    @Override
    double getLikelyhood(Optional<Level> level) {
        if (level.isPresent()) {
            if (level.get() == Level.SILVER) {
                return 0.2;
            } else if (level.get() == Level.GOLD) {
                return 0.3;
            } else if (level.get() == Level.PLATINUM) {
                return 0.4;
            }
        }
        return 0.1;
    }

    @Override
    int getValue(Optional<Level> level) {
        if (level.isPresent()) {
            if (level.get() == Level.SILVER) {
                return 10;
            } else if (level.get() == Level.GOLD) {
                return 10;
            } else if (level.get() == Level.PLATINUM) {
                return 30;
            }
        }
        return 3;
    }
}
