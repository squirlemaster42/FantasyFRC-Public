package com.fantasyfrc.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Constants {

    //Basic Constants
    public static final int DRAFT_SIZE = 8;
    public static final int SERVER_PORT = 0;
    private static final int year = 2019; //TODO update for 2020

    private static Constants instance;

    //Stores Blue Alliance Auth key
    private String TBAAuthKey;

    private final OkHttpClient client;
    private final Gson gson;

    //Stores valid event strings for championship fields
    private final ArrayList<String> champGameStrings;
    private File pathToCSV;

    //Stores scores for alliance selection
    private final int[][] allianceSelectionScoringGuide;

    //Store configs loaded from .conf files
    private final Map<String, String> confMap;

    public static Constants getInstance(){
        if(instance == null) {
            instance = new Constants();
        }
        return instance;
    }

    private Constants(){
        //Adds event strings to champ event list
        this.champGameStrings = new ArrayList<>();
        champGameStrings.add(year + "carv");
        champGameStrings.add(year + "gal");
        champGameStrings.add(year + "hop");
        champGameStrings.add(year + "new");
        champGameStrings.add(year + "roe");
        champGameStrings.add(year + "tur");
        champGameStrings.add(year + "arc");
        champGameStrings.add(year + "cars");
        champGameStrings.add(year + "cur");
        champGameStrings.add(year + "dal");
        champGameStrings.add(year + "dar");
        champGameStrings.add(year + "tes");

        this.gson = new Gson();
        this.client = new OkHttpClient();

        //Makes alliance selection scoring guide
        allianceSelectionScoringGuide = new int[][]{
                {24, 20, 16, 12},
                {23, 19, 15, 11},
                {22, 18, 14, 10},
                {21, 17, 13, 9},
                {20, 16, 12, 8},
                {19, 15, 11, 7},
                {18, 14, 10, 6},
                {17, 13, 9, 5}
        };

        confMap = new HashMap<>();
    }

    public int[][] getAllianceSelectionScoringGuide(){
        return this.allianceSelectionScoringGuide;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Gson getGson() {
        return gson;
    }

    public ArrayList<String> getChampGameStrings(){
        return this.champGameStrings;
    }

    public String getTBAAuthKey(){
        return this.TBAAuthKey;
    }

    public File getPathToCSV(){
        return pathToCSV;
    }

    public void setPathToCSV(File file){
        this.pathToCSV = file;
    }

    public void setTBAAuthKey(String key){
        this.TBAAuthKey = key;
    }

    public String getConfig(String conf){
        return confMap.get(conf);
    }

    //Config format is name: config TODO Test
    public void loadConfig(final File confFile){
        try {
            Scanner scanner = new Scanner(confFile);
            String line = scanner.nextLine();
            while (line != null){
                line = line.replaceAll(":", "");
                String[] splitLine = line.split(" ");
                confMap.put(splitLine[0], splitLine[1]);
                line = scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
