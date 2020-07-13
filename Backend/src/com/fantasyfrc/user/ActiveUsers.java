package com.fantasyfrc.user;

import java.util.HashMap;
import java.util.Map;

public final class ActiveUsers {

    //TODO Figure out how to deauth users
    private static ActiveUsers instance;

    public static ActiveUsers getInstance(){
        if(instance == null){
            instance = new ActiveUsers();
        }
        return instance;
    }

    private final Map<String, User> users;

    private ActiveUsers(){
        this.users = new HashMap<>();
    }

    public void addUser(final String username, final User user){
        users.put(username, user);
    }

    public User getUser(final String username){
        return users.get(username);
    }

    public void deauthUser(final String username){
        users.remove(username);
    }
}
