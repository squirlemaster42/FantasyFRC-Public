package com.fantasyfrc.scoring.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AwardMap {

    private static AwardMap instance;

    public static AwardMap getInstance() {
        if(instance == null){
            instance = new AwardMap();
        }
        return instance;
    }

    private final Map<Integer, Integer> awardMap;

    private AwardMap(){
        awardMap = new ConcurrentHashMap<>();
        awardMap.put(0, 75); //Chairmans winner
        awardMap.put(69, 25); //Chairmans finalist
        awardMap.put(1, 24); //Sub div winner
        awardMap.put(2, 18); //Finalist
    }

    public int getScore(final int awardCode){
        return awardMap.get(awardCode);
    }
}
