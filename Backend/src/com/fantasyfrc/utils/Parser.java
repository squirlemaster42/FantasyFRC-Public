package com.fantasyfrc.utils;

import com.fantasyfrc.scoring.utils.jsonobjects.alliances.Alliance;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    //Takes a list that in input as a string and parses it to an ArrayList
    public static ArrayList<String> listParser(String TBAString){
        ArrayList<String> outputList;

        //Removes [] in the front and back of the string
        TBAString = TBAString.replaceAll("\\[", "");
        TBAString = TBAString.replaceAll("\\]", "");

        //Splits the string at ","
        outputList = new ArrayList<>(Arrays.asList(TBAString.trim().split(",")));

        //Removes new lines
        for (int i = 0; i < outputList.size(); i++) {
            outputList.set(i, outputList.get(i).replaceAll("\"", "").replaceAll(",", "").trim());
        }

        return outputList;
    }

    //TODO need to figure out how to handle events that have not occurred yet
    public static Match[] parseEvent(final String jsonStr){
        return Constants.getInstance().getGson().fromJson(jsonStr, Match[].class);
    }

    public static Alliance[] getAlliances(final String eventStr, final String jsonStr){
        Alliance[] alliances = Constants.getInstance().getGson().fromJson(jsonStr, Alliance[].class);
        for(Alliance a : alliances){
            a.setEventStr(eventStr);
        }
        return alliances;
    }

}
