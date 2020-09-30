package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;

import java.util.PriorityQueue;
import java.util.Queue;

public class EinsteinRRScorer extends Scorer{

    private static final int WIN_POINTS = 18;
    //This is kept in case a change is make to the scoring structure
    private static final int RP1_POINTS = 0; //Balance RP
    private static final int RP2_POINTS = 0; //Wheel/Balls Scored RP

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
        super(new ScoreTemplate(WIN_POINTS, RP1_POINTS, RP2_POINTS));
        toScoreQueue = new PriorityQueue<>();
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

    int scoreAlliance(Match match, Alliance a){
        return super.scoreWithTemplate(match, a);
    }
}
