package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.exceptions.InvalidMatchException;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.team.Team;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class TeamScorer implements Runnable{

    private static TeamScorer instance;

    public static TeamScorer getInstance(){
        if(instance == null){
            instance = new TeamScorer();
        }
        return instance;
    }

    private final Queue<Team> teamsToScore;

    private Thread thread;
    private boolean running = false;

    private TeamScorer(){
        teamsToScore = new PriorityBlockingQueue<>();
    }

    @Override
    public void run() {
        while(running){
            if(teamsToScore.isEmpty()){
                continue;
            }
            Team toScore = teamsToScore.poll();
            //Score best of ones
            for(Match m : toScore.getMatches()){
                MatchType matchType;
                try{
                    matchType = MatchType.getMatchType(m);
                }catch (InvalidMatchException e){
                    continue;
                }
                switch(matchType){
                    case QUAL -> {
                        QualMatchScorer.getInstance().addMatchToScore(m);
                    }
                    case QUARTER, FINAL, SEMI, EINSTEINF -> {
                        //Needs to be scored as a whole
                    }
                    case EINSTEINRR -> {
                        EinsteinRRScorer.getInstance().addMatchToScore(m);
                    }
                }
            }

            //Score matches that are played as a series

            //Get Playoff Score

            //Get Einstein RR Score
        }
    }

    public synchronized  void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
