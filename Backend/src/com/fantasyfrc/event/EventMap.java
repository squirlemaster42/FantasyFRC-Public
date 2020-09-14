package com.fantasyfrc.event;

import com.fantasyfrc.scoring.QualMatchScorer;
import com.fantasyfrc.scoring.exceptions.ElimMatchException;
import com.fantasyfrc.scoring.exceptions.InvalidMatchException;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.utils.Constants;
import com.fantasyfrc.utils.Parser;
import com.fantasyfrc.utils.TBAReqGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventMap {

    private static EventMap instance;

    public static EventMap getInstance() {
        if(instance == null){
            instance = new EventMap();
        }
        return instance;
    }

    private final Map<String, List<Match>> eventMap;

    private EventMap(){
        eventMap = new ConcurrentHashMap<>();
        try {
            update();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() throws IOException {
        //TODO Complete addition of all required functionality
        //Updates the events in the map
        //Will not update events that have already been competed
        //Will not update after event has closed for the day
        //This method is called on user login or when a button is pressed
        //In the event that the button is spammed, the client side is expected to restrict
        // this function to being called to once every 10 minutes

        for(String event : Constants.getInstance().getChampGameStrings()){
            eventMap.put(event, Arrays.asList(Parser.parseEvent(TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventMatchRequest(event)))));
        }

//        eventMap.forEach((k, v) -> v.forEach(m -> {
//            try {
//                //TODO Reimplement
//                //QualMatchScorer.scoreMatch(m);
//            } catch (InvalidMatchException e) {
//                e.printStackTrace();
//            } catch (ElimMatchException e) {
//                System.err.println("Trying to score elim match");
//            }
//        }));
    }

    public void addEvent(String eventId) throws IOException {
        eventMap.put(eventId, Arrays.asList(Parser.parseEvent(TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventMatchRequest(eventId)))));
    }

    public List<Match> getMatchesForEvent(final String eventID){
        return eventMap.get(eventID);
    }

    public Match getMatchFromEvent(final String matchID){
        String[] inp = matchID.split("_"); //First element is eventID second is the matchID
        List<Match> matchList = eventMap.get(inp[0]);
        for (Match m : matchList) {
            if(m.getKey().equals(matchID)){
                return m;
            }
        }
        return null;
    }
}
