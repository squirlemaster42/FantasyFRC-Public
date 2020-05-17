package com.fantasyfrc.user;

import com.fantasyfrc.utils.LoginManager;
import com.fantasyfrc.utils.Constants;

import java.util.ArrayList;

public class User {

    //TODO Give user id
    public static boolean createUser(final String username, final String password) {
        boolean userCreated = LoginManager.authUser(username, password);
        if (!userCreated) {
            return false;
        }
        //TODO Change to use userID
        User user = new User(username, Constants.getInstance().getGson().fromJson(UserDatabaseManager.getInstance().getDraftsForUser(username), DraftList.class));
        ActiveUsers.getInstance().addUser(username, user);
        return true;
    }

    private final String username;
    private final DraftList draftList;

    private User(final String username, final DraftList drafts){
        this.username = username;
        if(drafts == null){
            draftList = new DraftList();
        }else{
            draftList = drafts;
        }
    }

    public void addDraft(final String draft){
        draftList.addDraft(draft);
    }

    public ArrayList<String> getDraftList() {
        return draftList.getDrafts();
    }

    public String getUsername(){
        return username;
    }

    public String genJsonString(){
        return Constants.getInstance().getGson().toJson(draftList);
    }

    void writeUser(){
        UserDatabaseManager.getInstance().updateUserInDB(this);
    }

    @Override
    public String toString(){
        return username;
    }

    private static class DraftList{
        private final ArrayList<String> drafts;

        private DraftList(){
            drafts = new ArrayList<>();
        }

        protected void addDraft(final String draft){
            drafts.add(draft);
        }

        ArrayList<String> getDrafts(){
            return drafts;
        }
    }
}
