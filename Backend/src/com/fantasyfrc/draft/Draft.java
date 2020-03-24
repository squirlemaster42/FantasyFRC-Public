package com.fantasyfrc.draft;

import com.fantasyfrc.utils.Constants;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public class Draft {

    final String id;
    private String[] pickOrder;
    private Pick[] picks = new Pick[64];
    private int lastPickTime = Integer.MIN_VALUE;
    private int lastPick = -1;

    //TODO Need to add teams to the TeamMap on load from database and on pick


    //TODO Give drafts some sort of name
    //Inits draft from a list of players
    Draft(String[] players){
        //TODO This is bad because if two drafts have the same players we could get same UUID?
        //Might want to make it so UUID is based on draft name and time it was created
        id = UUID.nameUUIDFromBytes(Arrays.toString(players).getBytes()).toString();
        Collections.shuffle(Arrays.asList(players));
        pickOrder = players;
        int pos = 0;
        for(int i = 0; i < picks.length / 16; i ++){
            for(int j = 0; j < pickOrder.length; j++){
                picks[pos] = new Pick(pickOrder[j], this);
                pos++;
            }
            for(int j = pickOrder.length - 1; j >= 0; j--){
                picks[pos] = new Pick(pickOrder[j], this);
                pos++;
            }
        }
        System.out.println(Arrays.deepToString(pickOrder));
        for(int i = 0; i < picks.length; i++){
            if(i % 8 == 0){
                System.out.println();
            }
            System.out.print(picks[i] + " ");
        }
        picks[lastPick + 1].unlock();

        DraftMap.getInstance().addDraft(id, this);
        //TODO Make sure that this is working
        if(!DraftDatabaseManager.getInstance().draftExists(id)){
            try {
                Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
                String sqlStr = String.format("insert into drafts values (\'%s\', \'%s\');", id, genJSONStr());
                System.out.println(sqlStr);
                DraftDatabaseManager.getInstance().write(statement, sqlStr);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            System.err.println("Draft already exists"); //TODO Figure out how to handle this
        }
    }

    //Inits draft from the database
    Draft(final String id){
        String id1;
        try {
            Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
            String result = DraftDatabaseManager.getInstance().read(statement, String.format("select * from drafts where id = \"%s\";", id));
            Draft draft =  Constants.getInstance().getGson().fromJson(result, Draft.class);
            id1 = id;
            pickOrder = draft.pickOrder;
            picks = draft.picks;
            lastPick = draft.lastPick;
            lastPickTime = draft.lastPickTime;
            for(Pick pick : picks){
                pick.setDraft(this);
            }
            DraftMap.getInstance().addDraft(id, this);
        } catch (SQLException e) {
            e.printStackTrace();
            id1 = "";
        }
        this.id = id1;
    }

    //TODO Figure out how to handle invalid picks
    //TODO Needs to check that a team has not already been picked
    public void makePick(final String username, final String teamID) {
        System.out.println("Pick of: " + teamID + " from: " + username);
        //&& picks[lastPick + 1].getUser().equals(username)
        if(!picks[lastPick + 1].isLocked()){
            picks[lastPick + 1].makePick(teamID);
            lastPick++;
            picks[lastPick + 1].unlock();
            lastPickTime = Instant.now().getNano(); //TODO Check
            updateDatabase();
        }
    }

    private String genJSONStr(){
        return Constants.getInstance().getGson().toJson(this);
    }

    //Might want to move to the DraftDatabaseManager
    private void updateDatabase(){
        try {
            Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
            String sqlStr = String.format("update drafts set drafts = \'%s\' where id = \'%s\';", genJSONStr(), id);
            DraftDatabaseManager.getInstance().write(statement, sqlStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getID() {
        return id;
    }

    public String[] getPickOrder(){
        return pickOrder;
    }

    @Override
    public String toString() {
        return Arrays.toString(picks);
    }

    //TODO Change
    public String getName(){
        return id;
    }
}
