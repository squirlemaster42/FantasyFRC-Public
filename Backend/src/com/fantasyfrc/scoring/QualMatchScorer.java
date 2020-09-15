package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.scoring.utils.jsonobjects.match.ScoreBreakdown;
import com.fantasyfrc.utils.Constants;

import java.util.PriorityQueue;
import java.util.Queue;

//Make the scorer separate from the website
//The scorers job is to populate the sql database
//Store the expected time that the match will be played (plus time to play match and have data updated in TBA)
//Try to score the match then
//If the match cannot be scored (likely because it has not been played)
//increase the time it should be scored by x minutes (5?)
//Need to figure out how replays are scheduled on TBA
//If it is not clear on TBA, need to check for score change at the end of the day\
//This should run on its own thread

//Might want to store the matches to be scored in a priority queue
//Priority would be given based on the time the match is to be played

//Playoff matches will not be scored along with Einstein finals
//When a match is scored, the database entry for the teams in the match will be updated
//After finals for each comp (divisions count as a comp as well as Einstein) will score playoffs
//EinsteinRR can be scored in the normal way as it is not played in a best of x series
//After the event, awards will be scored
//These scored will be added to the team's score in the database

//May want to separate match scorer and team scorer
//Match scorer will score only qual matches
//Team scorer will score everything else since that it more team specific and is not score base on the individual match

public class QualMatchScorer extends Scorer{

    private static final int WIN_POINTS = 2;
    private static final int RP1_POINTS = 1; //Balance RP
    private static final int RP2_POINTS = 2; //Wheel/Balls Scored RP

    private static QualMatchScorer instance;

    public static QualMatchScorer getInstance(){
        if(instance == null){
            instance = new QualMatchScorer();
        }
        return instance;
    }

    private final Queue<Match> toScoreQueue;

    private Thread thread;
    private boolean running = false;

    private QualMatchScorer(){
        super(new ScoreTemplate(WIN_POINTS, RP1_POINTS, RP2_POINTS));
        toScoreQueue = new PriorityQueue<>();
    }

    public void addMatchToScore(final Match m){
        toScoreQueue.add(m);
    }

    Match pollMatch(){
        return toScoreQueue.poll();
    }

    @Override
    public void run() {
        while(running){
            //Score next team from queue
            Match toScore = toScoreQueue.peek();
            if(toScore == null){
                //There are no matches to score
                return;
            }else{
                if(scoreMatch(toScore)){
                    toScoreQueue.poll();
                }else{
                    //Wait a period of time
                    try {
                        thread.wait(Constants.WAIT_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
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
