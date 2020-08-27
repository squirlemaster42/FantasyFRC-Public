package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.scoring.utils.jsonobjects.match.ScoreBreakdown;
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

public class QualMatchScorer implements Runnable{

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
                        thread.wait(300000);
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


    //TODO Update comments for 2020
    private static final int WIN_POINTS = 3;
    private static final int RP1_POINTS = 1; //Climb RP
    private static final int RP2_POINTS = 2; //Rocket RP

    //https://github.com/squirlemaster42/Fantasy-FRC/blob/master/Back%20End/FantasyFRCBackend/src/com/onion/scoring/Scorer.java
    static boolean scoreMatch(Match match) {
        //TODO Check if match is already scored
        int redScore = scoreQual(match, Alliance.RED);
        int blueScore = scoreQual(match, Alliance.BLUE);

        match.setRedScore(redScore);
        match.setBlueScore(blueScore);
        //TODO Account for replays
        match.setScored(redScore > 0 || blueScore > 0); //This will work because at least one team will get a ranking point because at least one team has to win or there is a tie

        QualMatchDatabaseManager.getInstance().updateScore(match.getKey(), redScore, blueScore);
        return redScore != 0 || blueScore != 0;
    }

    private static int scoreQual(Match match, Alliance a){
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
