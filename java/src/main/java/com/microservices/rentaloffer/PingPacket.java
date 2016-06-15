// Look at using quick-json library from Google as alternative

package com.microservices.rentaloffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static com.microservices.rentaloffer.NeedPacket.NEED;

public class PingPacket {
    private String id;
    private String userid;
    private Level level;
    private List<String> signs = new ArrayList<>();
    private int readCount = 0;

    public PingPacket() {
        id = IDProvider.getId();
    }

    private final List<Reply> replies = new ArrayList<>();

    public String toJson(String sign) {
        signs.add(sign);
        return toJson();
    }

    private String toJson() {
        Map<String, Object> message = new HashMap<>();
        message.put("json_class", PingPacket.class.getName());
        message.put("id", id);
        message.put("readCount", readCount);
        message.put("replies", replies);
        message.put("signs", signs);
        return new Gson().toJson(message);
    }

    public String getUserid() {
        return userid;
    }

    public Level getLevel() {
        return level;
    }

    public int getReadCount() {
        return readCount;
    }

    public void increaseReadCount() {
        readCount++;
    }

    public static PingPacket fromJson(String json) {
        return new Gson().fromJson(json, PingPacket.class);
    }

    public void addReply(Reply reply) {
        replies.add(reply);
    }

    public boolean hasNoSolutions() {
        return replies.size() == 0;
    }

    public boolean hasSolutions() {
        return replies.size() > 0;
    }

    public List<Reply> getReplies() {
        return replies;
    }


    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
