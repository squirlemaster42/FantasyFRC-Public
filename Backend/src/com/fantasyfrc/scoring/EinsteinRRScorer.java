package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.scoring.utils.jsonobjects.match.ScoreBreakdown;

import java.util.PriorityQueue;
import java.util.Queue;

public class EinsteinRRScorer implements Runnable{


    private static EinsteinRRScorer instance;

    public static EinsteinRRScorer getInstance(){
        if(instance == null){
            instance = new EinsteinRRScorer();
        }
        return instance;
    }

    private final Queue<Match> toScoreQueue;

    private Thread thread;
    private boolean running = false;

    private EinsteinRRScorer(){
        toScoreQueue = new PriorityQueue<>(); //TODO Figure out how to assign priority to match
    }

    public void addMatchToScore(final Match m){
        toScoreQueue.add(m);
    }

    @Override
    public void run() {
        while(running){
            //Score next team from queue
            Match toScore = toScoreQueue.poll();
            if(toScore == null){
                //There are no matches to score
                return;
            }

            scoreMatch(toScore);
        }
    }

    //TODO try to make less accessible, protected/default/private
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //TODO Update comments for 2020
    private static final int WIN_POINTS = 3;
    private static final int RP1_POINTS = 1; //Climb RP
    private static final int RP2_POINTS = 2; //Rocket RP

    //https://github.com/squirlemaster42/Fantasy-FRC/blob/master/Back%20End/FantasyFRCBackend/src/com/onion/scoring/Scorer.java
    static void scoreMatch(Match match) {
        //TODO Check if match is already scored
        int redScore = scoreRR(match, Alliance.RED);
        int blueScore = scoreRR(match, Alliance.BLUE);

        match.setRedScore(redScore);
        match.setBlueScore(blueScore);
        //TODO Account for replays
        match.setScored(redScore > 0 || blueScore > 0); //This will work because at least one team will get a ranking point because at least one team has to win or there is a tie

        MatchDatabaseManager.getInstance().updateScore(match.getKey(), redScore, blueScore);
    }

    private static int scoreRR(Match match, Alliance a){
        //TODO Update for 2020
        int score = 0;

        String alliance = a == Alliance.RED ? "red" : "blue";

        ScoreBreakdown results = match.getScore_breakdown().get(alliance);
        if (match.getWinning_alliance().equals(alliance)) {
            score += WIN_POINTS;
        }else if(match.getWinning_alliance().equals("")){
            //There is a tie
            score += 1;
        }
        if (results.isHabDockingRankingPoint()) {
            score += RP1_POINTS;
        }
        if (results.isCompleteRocketRankingPoint()) {
            score += RP2_POINTS;
        }
        return score;
    }
}
