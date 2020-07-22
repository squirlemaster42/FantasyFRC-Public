package com.fantasyfrc.team;

import com.fantasyfrc.event.EventMap;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.utils.Constants;
import com.fantasyfrc.utils.Parser;
import com.fantasyfrc.utils.TBAReqGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {

    private final String teamID;
    private int score;
    private int lastMatchScored = 0;

    private final ArrayList<Match> matches;

    public Team(final String teamID){
        this.teamID = teamID;
        matches = new ArrayList<>();
        addMatches();
        //update();
    }

//    public void update(){
//        for (Match match : matches){
//            if(Arrays.asList(match.getAlliances().get("red").getTeam_keys()).contains(getTeamID())){
//                score += match.getRedScore();
//            }else{
//                score += match.getBlueScore();
//            }
//            if(match.matchScored()){
//                lastMatchScored = matches.indexOf(match);
//            }
//        }
//
//    }

    void addMatches(){
        List<String> matchStrings = new ArrayList<>();
        try {
            for(String event : Constants.getInstance().getChampGameStrings()){
                matchStrings.addAll(Parser.listParser(TBAReqGenerator.makeRequest(TBAReqGenerator.makeMatchListRequest(event, getTeamID()))));
            }
            matchStrings.forEach(s -> {
                if(!s.equals("")){
                    matches.add(EventMap.getInstance().getMatchFromEvent(s));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScore(){
        return score;
    }

    public String getTeamID(){
        return teamID;
    }

    public List<Match> getMatches(){
        return matches;
    }
}
