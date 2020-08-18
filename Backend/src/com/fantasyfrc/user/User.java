package com.fantasyfrc.user;

import com.fantasyfrc.utils.LoginManager;
import com.fantasyfrc.utils.Constants;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User {

    public static boolean initUser(final String username, final String password) {
        boolean userCreated = LoginManager.authUser(username, password);
        if (!userCreated) {
            return false;
        }
        User user = new User(username, Constants.getInstance().getGson().fromJson(UserDatabaseManager.getInstance().getDraftsForUser(username), DraftList.class));
        ActiveUsers.getInstance().addUser(username, user);
        return true;
    }

    public static boolean createUser(String username){
        if(UserDatabaseManager.getInstance().doesUserExists(username)) {
            return false;
        }

        return true;
    }

    private final String username;
    private final DraftList draftList;
    private final String id;

    private User(final String username, final DraftList drafts){
        this.username = username;
        this.id = UUID.randomUUID().toString();
        draftList = Objects.requireNonNullElseGet(drafts, DraftList::new);
        if(UserDatabaseManager.getInstance().getUserID(username) == null){
            UserDatabaseManager.getInstance().updateID(this);
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

    public String getId(){
        return id;
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
