package com.hazelcast.demo.cloud;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;

@Component
public class SomeService {

    private static final Logger logger = LoggerFactory.getLogger(SomeService.class);

    private final HazelcastInstance hazelcastInstance;

    public SomeService(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @EventListener
    public void onApplicationIsReady(ContextRefreshedEvent contextRefreshedEvent) {
        var mapName = "MyMap";
        Map<String, String> myMap = hazelcastInstance.getMap(mapName);
        for (int i = 0; i < 10; i++) {
            myMap.put(UUID.randomUUID().toString(), "Value-" + i);
        }
        logger.info("Map prepopulated [mapName={},size={}]", mapName, myMap.size());
    }

}
