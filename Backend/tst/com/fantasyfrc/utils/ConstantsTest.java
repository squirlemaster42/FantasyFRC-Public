package com.fantasyfrc.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ConstantsTest {

    @Test
    public void testLoadConfigs(){
        try {
            Constants.getInstance().loadConfig("sql", "sqlconfig.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("root", Constants.getInstance().getConfig("sql").getProperty("username"));
        assertEquals("password", Constants.getInstance().getConfig("sql").getProperty("password"));
        assertEquals("jdbc:mysql://localhost:3306/users", Constants.getInstance().getConfig("sql").getProperty("url"));
    }
}
