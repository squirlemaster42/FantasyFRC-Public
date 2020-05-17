package com.fantasyfrc.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ConstantsTest {

    @Test
    public void testLoadConfigs(){
        Constants.getInstance().loadConfig(new File("web/sqlconfig.conf"));
        assertEquals("root", Constants.getInstance().getConfig("username"));
        assertEquals("password", Constants.getInstance().getConfig("password"));
    }
}
