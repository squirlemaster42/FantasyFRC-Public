package com.fantasyfrc.scoring.utils.jsonobjects.alliances;

import java.util.Arrays;

public class Alliance {
    private String eventStr;

    private String[] declines;

    private Backup backup;

    private String name;

    private String[] picks;

    private Status status;

    public String[] getDeclines() {
        return declines;
    }

    public void setDeclines(String[] declines) {
        this.declines = declines;
    }

    public Backup getBackup() {
        return backup;
    }

    public void setBackup(Backup backup) {
        this.backup = backup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPicks() {
        return picks;
    }

    public void setPicks(String[] picks) {
        this.picks = picks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setEventStr(final String eventStr){
        this.eventStr = eventStr;
    }

    public String getEventStr(){
        return eventStr;
    }

    @Override
    public String toString() {
        return "ClassPojo [declines = " + Arrays.toString(declines) + ", backup = " + backup + ", name = " + name + ", picks = " + Arrays.toString(picks) + ", status = " + status + "]";
    }
}
