package com.beyondlabs.controller;

public class RateBucket {
    private int count = 0;
    private long windowStart;
    
    public RateBucket(long now) {
        this.windowStart = now;
    }
    
    public synchronized boolean allow(int limit, long window) {
        long now = System.currentTimeMillis();
        if (now - windowStart >= window) {
            windowStart = now;
            count = 0;
        }
        return ++count <= limit;
    }
    
}
