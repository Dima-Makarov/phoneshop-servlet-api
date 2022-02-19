package com.es.phoneshop.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static final Long THRESHOLD = 20L;
    private final Map<String, Long> countMap = new ConcurrentHashMap<>();
    private long previousTimeMillis = System.currentTimeMillis();
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
        if (System.currentTimeMillis() - previousTimeMillis > 60 * 1000) {
            previousTimeMillis = System.currentTimeMillis();
            for (Map.Entry<String, Long> e : countMap.entrySet()) {
                e.setValue(0L);
            }
        }
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }
}
