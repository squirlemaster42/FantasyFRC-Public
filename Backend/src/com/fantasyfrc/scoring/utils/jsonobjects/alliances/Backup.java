package com.fantasyfrc.scoring.utils.jsonobjects.alliances;

public class Backup {
    private String in;

    private String out;

    public String getIn ()
    {
        return in;
    }

    public void setIn (String in)
    {
        this.in = in;
    }

    public String getOut ()
    {
        return out;
    }

    public void setOut (String out)
    {
        this.out = out;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [in = "+in+", out = "+out+"]";
    }
}
