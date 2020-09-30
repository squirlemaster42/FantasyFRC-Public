package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.utils.jsonobjects.alliances.Alliance;
import com.fantasyfrc.utils.Parser;
import com.fantasyfrc.utils.TBAReqGenerator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AllianceScorerTest {

    @Test
    public void testScoreAlliance(){
        String reqStr = null;
        try {
            reqStr = TBAReqGenerator.makeRequest(TBAReqGenerator.makeEventAllianceRequest("2019tur"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alliance[] alliances = Parser.getAlliances("2019tur", reqStr);
        assertEquals(24, AllianceScorer.getInstance().scoreAlliance(null, alliances[0]));
        assertEquals(6, AllianceScorer.getInstance().scoreAlliance(null, alliances[1]));
        assertEquals(6, AllianceScorer.getInstance().scoreAlliance(null, alliances[2]));
        assertEquals(6, AllianceScorer.getInstance().scoreAlliance(null, alliances[3]));
        assertEquals(12, AllianceScorer.getInstance().scoreAlliance(null, alliances[4]));
        assertEquals(18, AllianceScorer.getInstance().scoreAlliance(null, alliances[5]));
        assertEquals(12, AllianceScorer.getInstance().scoreAlliance(null, alliances[6]));
        assertEquals(6, AllianceScorer.getInstance().scoreAlliance(null, alliances[7]));
    }
}
