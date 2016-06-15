// Look at using quick-json library from Google as alternative

package com.microservices.rentaloffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class NeedPacket {
    private String id;
    private String userid;
    private Level level;
    private List<String> signs = new ArrayList<>();
    private int readCount = 0;

    public NeedPacket() {
        id = IDProvider.getId();
    }

    public static final String NEED = "car_rental_offer";
    private final List<Solution> solutions = new ArrayList<>();

    public String toJson(String sign) {
        signs.add(sign);
        return toJson();
    }

    private String toJson() {
        Map<String, Object> message = new HashMap<>();
        message.put("json_class", NeedPacket.class.getName());
        message.put("need", NEED);
        message.put("id", id);
        message.put("level", level);
        message.put("userid", userid);
        message.put("readCount", readCount);
        message.put("solutions", solutions);
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

    public static NeedPacket fromJson(String json) {
        return new Gson().fromJson(json, NeedPacket.class);
    }

    public void proposeSolution(Solution solution) {
        solutions.add(solution);
    }

    public boolean hasNoSolutions() {
        return solutions.size() == 0;
    }

    public boolean hasSolutions() {
        return solutions.size() > 0;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
