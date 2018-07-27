package com.example.drakinosh.drpanda;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationId {
    private final static AtomicInteger c = new AtomicInteger(0);
    private final static AtomicInteger d = new AtomicInteger(0);
    public static int getId() {
        return c.incrementAndGet();
    }

    public static int getRequestCode() {
        return d.incrementAndGet();
    }
}
