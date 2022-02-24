package com.es.phoneshop.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultDosProtectionService implements DosProtectionService {
    private static class RequestData {
        public RequestData(int count, long lastRequestTime) {
            this.count = count;
            this.lock = new ReentrantLock();
            this.lastRequestTime = lastRequestTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Lock getLock() {
            return lock;
        }

        public long getLastRequestTime() {
            return lastRequestTime;
        }

        public void setLastRequestTime(long lastRequestTime) {
            this.lastRequestTime = lastRequestTime;
        }

        private int count;
        private final Lock lock;
        private long lastRequestTime;
    }

    private static final Long THRESHOLD = 20L;
    private final Map<String, RequestData> requestMap = new ConcurrentHashMap<>();
    private static DosProtectionService instance = null;

    public static synchronized DosProtectionService getInstance() {
        if (instance == null) {
            instance = new DefaultDosProtectionService();
        }
        return instance;
    }

    private DefaultDosProtectionService() {

    }

    @Override
    public boolean isAllowed(String ip) {
        if (!requestMap.containsKey(ip)) {
            requestMap.put(ip, new RequestData(1, System.currentTimeMillis()));
            return true;
        } else {
            RequestData requestData = requestMap.get(ip);
            requestData.getLock().lock();
            try {
                if (System.currentTimeMillis() - requestData.getLastRequestTime() > 60 * 1000) {
                    requestData.setCount(0);
                }
                requestData.setLastRequestTime(System.currentTimeMillis());
                requestData.setCount(requestData.getCount() + 1);
                return requestData.getCount() < THRESHOLD;
            } finally {
                requestData.getLock().unlock();
            }
        }
    }
}