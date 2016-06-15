package com.microservices.rentaloffer;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Solution {

    private String id;
    private long value;
    private double likelyhood;

    public Solution() {
    }

    public Solution(long value, double likelyhood) {
        this.value = value;
        this.likelyhood = likelyhood;
        this.id = IDProvider.getId();
    }


    public String toJson() {
        Map<String, Object> message = new HashMap<>();
        message.put("json_class", Solution.class.getName());
        message.put("value", value);
        message.put("likelyhood", likelyhood);
        message.put("id", id);
        return new Gson().toJson(message);
    }


    public static Solution fromJson(String json) {
        return new Gson().fromJson(json, Solution.class);
    }

}
