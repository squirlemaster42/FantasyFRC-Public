package com.fantasyfrc.scoring.utils.jsonobjects.match;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Match implements Comparable{

    private int redScore = 0;
    private int blueScore = 0;
    private boolean matchScored = false;

    public int getRedScore() {
        return redScore;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }

    public void setScored(boolean scored){
        this.matchScored = scored;
    }

    public boolean matchScored() {
        return matchScored;
    }

    //Json Object
    long actual_time;
    Map<String, Alliance> alliances;
    String comp_level;
    String event_key;
    String key;
    int match_number;
    long post_result_time;
    long predicted_time;
    Map<String, ScoreBreakdown> score_breakdown;
    int set_number;
    long time;
    Video[] videos;
    String winning_alliance;

    public long getActual_time() {
        return actual_time;
    }

    public Map<String, Alliance> getAlliances() {
        return alliances;
    }

    public String getComp_level() {
        return comp_level;
    }

    public String getEvent_key() {
        return event_key;
    }

    public String getKey() {
        return key;
    }

    public int getMatch_number() {
        return match_number;
    }

    public long getPost_result_time() {
        return post_result_time;
    }

    public long getPredicted_time() {
        return predicted_time;
    }

    public Map<String, ScoreBreakdown> getScore_breakdown() {
        return score_breakdown;
    }

    public int getSet_number() {
        return set_number;
    }

    public long getTime() {
        return time;
    }

    public Video[] getVideos() {
        return videos;
    }

    public String getWinning_alliance() {
        return winning_alliance;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return Math.toIntExact(getPredicted_time() - ((Match) o).getPredicted_time());
    }
}
