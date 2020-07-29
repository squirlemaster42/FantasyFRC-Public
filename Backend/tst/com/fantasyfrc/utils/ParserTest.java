package com.fantasyfrc.utils;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class ParserTest {

    @Test
    public void testParseAlliance(){
        try {
            System.out.println(TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventAllianceRequest("2019tur")));
            System.out.println(Arrays.toString(Parser.getAlliances(TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventAllianceRequest("2019tur")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
