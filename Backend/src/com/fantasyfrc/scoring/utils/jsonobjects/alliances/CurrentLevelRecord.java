package com.fantasyfrc.scoring.utils.jsonobjects.alliances;

public class CurrentLevelRecord {
    private String wins;

    private String ties;

    private String losses;

    public String getWins ()
    {
        return wins;
    }

    public void setWins (String wins)
    {
        this.wins = wins;
    }

    public String getTies ()
    {
        return ties;
    }

    public void setTies (String ties)
    {
        this.ties = ties;
    }

    public String getLosses ()
    {
        return losses;
    }

    public void setLosses (String losses)
    {
        this.losses = losses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [wins = "+wins+", ties = "+ties+", losses = "+losses+"]";
    }
}
