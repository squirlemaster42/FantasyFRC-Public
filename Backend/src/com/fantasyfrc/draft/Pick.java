package com.fantasyfrc.draft;

public class Pick {

    private final String user;
    private transient Draft draft;
    private String pick;
    private boolean locked;

    Pick(final String user, final Draft draft){
        this.user = user;
        this.draft = draft;
        locked = true;
    }

    void makePick(final String pick){
        if(!locked){
            this.pick = pick;
            locked = true;
        }
    }

    public String getPick(){
        return pick == null ? "" : pick;
    }

    void lock(){
        locked = true;
    }

    void unlock(){
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getUser() {
        return user == null ? "" : user;
    }

    public Draft getDraft(){
        return draft;
    }


    @Override
    public String toString() {
        return "Pick{" +
                "user='" + user + '\'' +
                ", pick='" + pick + '\'' +
                ", locked=" + locked +
                '}';
    }
}
