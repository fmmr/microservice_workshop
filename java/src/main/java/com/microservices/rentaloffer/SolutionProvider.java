package com.microservices.rentaloffer;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microservices.rentaloffer.PacketUtil.isPingPacket;

public abstract class SolutionProvider implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(SolutionProvider.class);
    private static Connections connection;

    public SolutionProvider(Connections connection) {
        this.connection = connection;
    }


    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);
        if (isPingPacket(message)) {
            final PingPacket pingPacket = PingPacket.fromJson(message);
            pingPacket.increaseReadCount();
            connection.publish(pingPacket.toJson(sign()));
        } else {
            if (needPacket.getReadCount() > 9) {
                logger.error("Need packet read more than 9 times: " + needPacket);
                return;
            }

            if (shouldProvideNewSolution(needPacket)) {
                Optional<Level> level = Optional.ofNullable(needPacket.getLevel());
                needPacket.proposeSolution(new Solution(getType(), getValue(level), getLikelyhood(level)));
                needPacket.increaseReadCount();
                connection.publish(needPacket.toJson(sign()));
            }
        }
    }

    @Override
    public String sign() {
        return getType().toString();
    }

    abstract SolutionType getType();

    abstract double getLikelyhood(Optional<Level> level);

    abstract int getValue(Optional<Level> level);

    private boolean shouldProvideNewSolution(NeedPacket needPacket) {
        return needPacket.getSolutions().size() == 0;
    }

}
