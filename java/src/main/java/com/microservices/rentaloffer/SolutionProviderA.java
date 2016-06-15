package com.microservices.rentaloffer;

import java.util.List;

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
    double getLikelyhood() {
        return 0.5;
    }

    @Override
    int getValue() {
        return 4;
    }
}
