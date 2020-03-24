package com.fantasyfrc.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SettingLoader {

    private final String fileLocation;

    //TODO Figure out if String if the best way to store the value of the setting
    private final Map<String, String> settingMap;

    public SettingLoader(final String fileLocation){
        this.fileLocation = fileLocation;
        settingMap = new HashMap<>();
        try {
            loadSettings();
        } catch (IOException e) {
            System.err.println("Could not load settings");
            System.err.println("Exiting...");
            System.exit(100);
        }
    }

    /*
     * Setting Format:
     * Setting_name: setting_value
     */
    private void loadSettings() throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(new File(fileLocation)));
        String line;
        while ((line = reader.readLine()) != null){
            line = line.replaceAll(" ", "");
            String[] split = line.split(":");
            settingMap.put(split[0], split[1]);
        }
    }

    /**
     * Gets the value of the setting
     * @param settingName The name of the setting
     * @return The value of the setting, null is no settings of the specified name exists
     */
    public String getSetting(final String settingName){
        if(settingMap.containsKey(settingName)){
            return settingMap.get(settingName);
        }
        return null;
    }
}
