package com.fantasyfrc.draft;

import com.fantasyfrc.utils.Constants;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class Draft {

    private final String id;
    private final Pick[] picks = new Pick[64];
    private long lastPickTime = Integer.MIN_VALUE;
    private int lastPick = -1;
    private String[] players;



    /**
     * Creates draft from a list of players
     *
     * @param id      A name/id for the draft (Must be unique)
     * @param players The players in the draft
     */
    //TODO Verify ID is unique prior to creating draft
    public Draft(String id, String[] players) {
        this.id = id;
        Collections.shuffle(Arrays.asList(players));
        this.players = players;
        for (int i = 0; i < picks.length; i++) {
            picks[i] = new Pick(players[i % 8], this);
        }

        picks[lastPick + 1].unlock();

        DraftDatabaseManager.getInstance().updateDatabase(this);
    }

    //TODO Figure out how to handle invalid picks
    //TODO Needs to check that a team has not already been picked
    //TODO Check that team is at event
    //TODO Remove username field
    public void makePick(final String username, final String teamID) {
        System.out.println("Pick of: " + teamID + " from: " + username);
        //&& picks[lastPick + 1].getUser().equals(username)
        if (!picks[lastPick + 1].isLocked()) {
            picks[lastPick + 1].makePick(teamID);
            lastPick++;
            picks[lastPick + 1].unlock();
            lastPickTime = System.nanoTime();
            DraftDatabaseManager.getInstance().updateDatabase(this);
        }
    }

    String genJSONStr() {
        return Constants.getInstance().getGson().toJson(this);
    }

    public String getId() {
        return id;
    }

    public String[] getPlayers(){
        return players;
    }

    public Pick[] getPicks() {
        return picks;
    }

    @Override
    public String toString() {
        return Arrays.toString(picks);
    }
}
