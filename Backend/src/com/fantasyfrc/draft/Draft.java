package com.fantasyfrc.draft;

import com.fantasyfrc.utils.Constants;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class Draft {

    private final String id;
    private String[] pickOrder;
    private Pick[] picks = new Pick[64];
    private long lastPickTime = Integer.MIN_VALUE;
    private int lastPick = -1;

    /**
     * Creates draft from a list of players
     *
     * @param id      A name/id for the draft (Must be unique)
     * @param players The players in the draft
     */
    //TODO Verify ID is unique prior to creating draft
    Draft(String id, String[] players) {
        this.id = id;
        Collections.shuffle(Arrays.asList(players));
        pickOrder = players;
        int pos = 0;
        for (int i = 0; i < picks.length / 16; i++) {
            for (int j = 0; j < pickOrder.length; j++) {
                picks[pos] = new Pick(pickOrder[j], this);
                pos++;
            }
            for (int j = pickOrder.length - 1; j >= 0; j--) {
                picks[pos] = new Pick(pickOrder[j], this);
                pos++;
            }
        }

        picks[lastPick + 1].unlock();

        try {
            DraftDatabaseManager.getInstance().addDraft(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO Figure out how to handle invalid picks
    //TODO Needs to check that a team has not already been picked
    //TODO Check that team is at event
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

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return Arrays.toString(picks);
    }
}
