package com.fantasyfrc.utils;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Constants {

    //Basic Constants
    public static final int DRAFT_SIZE = 8;
    public static final int SERVER_PORT = 0;
    private static final int year = 2019; //TODO update for 2020

    private static Constants instance;

    private final OkHttpClient client;
    private final Gson gson;

    //Stores valid event strings for championship fields
    private final ArrayList<String> champGameStrings;
    private File pathToCSV;

    //Stores scores for alliance selection
    private final int[][] allianceSelectionScoringGuide;

    //Store configs loaded from .conf files
    private final Map<String, Properties> confMap;

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
        }
        return instance;
    }

    private Constants() {
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

    public int[][] getAllianceSelectionScoringGuide() {
        return this.allianceSelectionScoringGuide;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Gson getGson() {
        return gson;
    }

    public ArrayList<String> getChampGameStrings() {
        return this.champGameStrings;
    }

    public String getTBAAuthKey() {
        return confMap.get("tba").getProperty("tbakey");
    }

    public File getPathToCSV() {
        return pathToCSV;
    }

    public void setPathToCSV(File file) {
        this.pathToCSV = file;
    }

    public Properties getConfig(String conf) {
        return confMap.get(conf);
    }

    //Config format is name: config
    public void loadConfig(final String propName, final String confFile) throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(confFile);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + confFile + "' not found in classpath");
        }

        confMap.put(propName, prop);
    }

}
