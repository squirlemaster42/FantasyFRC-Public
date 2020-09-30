package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;
import com.fantasyfrc.scoring.utils.jsonobjects.match.ScoreBreakdown;

public abstract class Scorer implements Runnable{

   record ScoreTemplate(int WIN_POINTS, int RP1_POINTS, int RP2_POINTS){}

   private ScoreTemplate template;

    public Scorer(ScoreTemplate template){
        this.template = template;
    }

    boolean scoreMatch(Match match){
        //TODO Check if match needs to be scored
        int redScore = scoreAlliance(match, Alliance.RED);
        int blueScore = scoreAlliance(match, Alliance.BLUE);

        match.setRedScore(redScore);
        match.setBlueScore(blueScore);
        //TODO Account for replays
        match.setScored(redScore > 0 || blueScore > 0);

        QualMatchDatabaseManager.getInstance().updateScore(match.getKey(), redScore, blueScore);
        return redScore > 0 || blueScore > 0;
    }

    abstract int scoreAlliance(Match match, Alliance alliance);

    int scoreWithTemplate(Match match, Alliance alliance){
        int score = 0;

        String allianceStr = alliance == Alliance.RED ? "red" : "blue";

        ScoreBreakdown result = match.getScore_breakdown().get(allianceStr);
        if(match.getWinning_alliance().equals(allianceStr)){
            score += template.WIN_POINTS;
        }else if(match.getWinning_alliance().equals("")){
            //There was a tie
            score += 1;
        }
        if(result.getShieldOperationalRankingPoint()){
            score += template.RP1_POINTS;
        }
        if(result.getShieldEnergizedRankingPoint()){
            score += template.RP2_POINTS;
        }

        return score;
    }
}
