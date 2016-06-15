package com.microservices.rentaloffer;

public class Reply {

    private String id;
    private String sign;

    public Reply() {
    }

    public Reply(String sign) {
        this.sign = sign;
        this.id = IDProvider.getId();
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "sign=" + sign +
                '}';
    }
}
