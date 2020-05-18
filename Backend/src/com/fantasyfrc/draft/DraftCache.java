package com.fantasyfrc.draft;

import com.fantasyfrc.utils.LRUCache;

public final class DraftCache {

    private static DraftCache instance;

    public static DraftCache getInstance() {
        if(instance == null){
            instance = new DraftCache();
        }
        return instance;
    }

    private final LRUCache<String, Draft> cache;

    private DraftCache(){
        //TODO Determine starting capacity
        cache = new LRUCache<>(DraftDatabaseManager.getInstance().getDataProvider(), 1000);
    }

    public Draft getDraft(final String id){
        return cache.get(id);
    }
}
