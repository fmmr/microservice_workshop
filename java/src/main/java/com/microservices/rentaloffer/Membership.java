package com.microservices.rentaloffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Membership implements MessageHandler {

    protected static Logger logger = LoggerFactory.getLogger(Membership.class);
    private static Connections connection;

    public static void main(String[] args) {
        String host = args[0];
        String port = args[1];

        connection = new Connections(host, port);
        connection.deliveryLoop(new Membership());
    }

    public void handle(String message) {
        final NeedPacket needPacket = NeedPacket.fromJson(message);

        if (needPacket.getReadCount() > 9) {
            logger.error("Message read more than 9 times: " + needPacket);
            return;
        }

        if (shouldHandle(needPacket)) {
            final String userid = needPacket.getUserid();
            Level level = getLevelForUser(userid);
            if (level != null) {
                needPacket.setLevel(level);
            } else {
                needPacket.proposeSolution(new Solution(SolutionType.JOIN, 500, 0.3));
            }
            needPacket.increaseReadCount();
            connection.publish(needPacket.toJson(sign()));
        }
    }

    @Override
    public String sign() {
        return "Membership";
    }

    private boolean shouldHandle(NeedPacket needPacket) {
        final boolean hasLevel = needPacket.getLevel() != null;
        final String userid = needPacket.getUserid();

        if (hasLevel) {
            return false;
        }

        if (userid == null) {
            return false;
        }


        needPacket.getSolutions().size();

        return needPacket.getSolutions().size() == 0;
    }

    private Level getLevelForUser(String userid) {
        if ("Lotte".equals(userid)) {
            return Level.PLATINUM;
        } else if ("Henning".equals(userid)) {
            return Level.GOLD;
        } else if ("Fredrik".equals(userid)) {
            return Level.SILVER;
        }
        return null;
    }
}
