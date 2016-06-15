package com.microservices.rentaloffer;

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
    double getLikelyhood() {
        return 0.8;
    }

    @Override
    int getValue() {
        return 6;
    }
}
