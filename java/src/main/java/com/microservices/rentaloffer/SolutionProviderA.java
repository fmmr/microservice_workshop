package com.microservices.rentaloffer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionProviderA extends SolutionProvider {

    protected static Logger logger = LoggerFactory.getLogger(SolutionProviderA.class);

    public SolutionProviderA(Connections connection) {
        super(connection);
    }

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        Connections connection = new Connections(host, port);
        connection.deliveryLoop(new SolutionProviderA(connection));
    }


    @Override
    SolutionType getType() {
        return SolutionType.A;
    }

    @Override
    double getLikelyhood(Optional<Level> level) {
        if (level.isPresent()) {
            if (level.get() == Level.SILVER) {
                return 0.5;
            } else if (level.get() == Level.GOLD) {
                return 0.8;
            } else if (level.get() == Level.PLATINUM) {
                return 0.8;
            }
        }
        return 0.1;
    }

    @Override
    int getValue(Optional<Level> level) {
        if (level.isPresent()) {
            if (level.get() == Level.SILVER) {
                return 6;
            } else if (level.get() == Level.GOLD) {
                return 6;
            } else if (level.get() == Level.PLATINUM) {
                return 12;
            }
        }
        return 3;
    }
}
