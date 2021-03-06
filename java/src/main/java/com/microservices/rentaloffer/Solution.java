package com.microservices.rentaloffer;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Solution {

    private String id;
    private SolutionType type;
    private long value;
    private double likelyhood;

    public Solution() {
    }

    public Solution(SolutionType type, long value, double likelyhood) {
        this.type = type;
        this.value = value;
        this.likelyhood = likelyhood;
        this.id = IDProvider.getId();
    }

    public SolutionType getType() {
        return type;
    }

    public double getLikelyhood() {
        return likelyhood;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "type=" + type +
                ", value=" + value +
                ", likelyhood=" + likelyhood +
                '}';
    }
}
