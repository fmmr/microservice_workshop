// Look at using quick-json library from Google as alternative

package com.microservices.rentaloffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.google.gson.Gson;

public class NeedPacket {
    private String id;

    public NeedPacket() {
        id = IDProvider.getId();
    }

    public static final String NEED = "car_rental_offer";
    private final List<Solution> solutions = new ArrayList<>();

    public String toJson() {
        Map<String, Object> message = new HashMap<>();
        message.put("json_class", NeedPacket.class.getName());
        message.put("need", NEED);
        message.put("id", id);
        message.put("solutions", solutions);
        return new Gson().toJson(message);
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

    public List<Solution> getSolutions() {
        return solutions;
    }
}
