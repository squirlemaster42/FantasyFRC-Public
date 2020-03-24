package com.fantasyfrc.utils;

import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static Match[] parseEvent(final String jsonStr){
        return Constants.getInstance().getGson().fromJson(jsonStr, Match[].class);
    }

}
