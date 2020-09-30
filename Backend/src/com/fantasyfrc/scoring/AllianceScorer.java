package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.alliances.Alliance;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class AllianceScorer extends Scorer{

    public static AllianceScorer instance;

    public static AllianceScorer getInstance(){
        if(instance == null){
            instance = new AllianceScorer();
        }
        return instance;
    }

    private final Queue<Alliance> toScore;

    private Thread thread;
    private boolean running = false;

    private AllianceScorer(){
        super(null);
        toScore = new PriorityBlockingQueue<>();
    }

    @Override
    public void run() {
        while(running){
            if(toScore.isEmpty()){
                return;
            }
            Alliance currScoring = toScore.poll();

            //Get Score
            int score = scoreAlliance(null, currScoring);

            //Add to database
            PlayoffDatabaseManager.getInstance().updateScore(new PlayoffDatabaseManager.AllianceScore(currScoring.getPicks(), score), currScoring.getEventStr());
        }
    }

    boolean scoreMatch(Match match){
        //Score match will not be used for alliances
        return false;
    }

    //TODO Figure out how to remove this
    @Override
    int scoreAlliance(Match match, com.fantasyfrc.scoring.Alliance alliance) {
        return 0;
    }

    int scoreAlliance(Match m, Alliance alliance){
        /*
         *  Quarter-finalist - 6 Points
         *  Semi-finalist - 12 Points
         *  Finalist - 18 Points
         *  Winner - 24 Points
         */

        return switch (alliance.getStatus().getLevel()){
            case "qf" -> 6;
            case "sf" -> 12;
            case "f"  -> {
                if(alliance.getStatus().getStatus().equals("won")){
                    yield 24;
                }else{
                    yield 18;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + alliance.getStatus().getLevel());
        };
    }

    public void addAllianceToScore(Alliance alliance){
        toScore.add(alliance);
    }

    public synchronized void start(){
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
