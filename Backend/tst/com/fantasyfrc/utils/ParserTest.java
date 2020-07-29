package com.fantasyfrc.utils;

import com.fantasyfrc.scoring.utils.jsonobjects.alliances.Alliance;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testParseAlliance(){
        try {
            String reqStr = TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventAllianceRequest("2019tur"));
            Alliance[] alliances = Parser.getAlliances(reqStr);
            assertEquals("frc254", alliances[0].getPicks()[0]);
            assertEquals("frc3310", alliances[0].getPicks()[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
