package com.fantasyfrc.scoring.utils.jsonobjects.alliances;

public class Status {
    private String playoff_average;

    private String level;

    private Record record;

    private CurrentLevelRecord currentLevelRecord;

    private String status;

    public String getPlayoff_average ()
    {
        return playoff_average;
    }

    public void setPlayoff_average (String playoff_average)
    {
        this.playoff_average = playoff_average;
    }

    public String getLevel ()
    {
        return level;
    }

    public void setLevel (String level)
    {
        this.level = level;
    }

    public Record getRecord ()
    {
        return record;
    }

    public void setRecord (Record record)
    {
        this.record = record;
    }

    public CurrentLevelRecord getCurrent_level_record ()
    {
        return currentLevelRecord;
    }

    public void setCurrentLevelRecord (CurrentLevelRecord currentLevelRecord)
    {
        this.currentLevelRecord = currentLevelRecord;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [playoff_average = "+playoff_average+", level = "+level+", record = "+record+", currentLevelRecord = "+currentLevelRecord+", status = "+status+"]";
    }
}
