package com.microservices.rentaloffer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IDProvider {
    private static AtomicLong counter = new AtomicLong();

    public static String getId() {
        final long l = counter.incrementAndGet();
        final String s = UUID.randomUUID().toString();
        return s;
    }
}
