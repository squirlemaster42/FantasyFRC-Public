package com.fantasyfrc.scoring;

import com.fantasyfrc.event.EventMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QualMatchScorerTest {

    @Test
    public void testScoreMatch(){
        //TODO Need to do threaded test
        EventMap.getInstance();
        QualMatchScorer.scoreMatch(EventMap.getInstance().getMatchFromEvent("2019tur_qm1"));
        QualMatchScorer.scoreMatch(EventMap.getInstance().getMatchFromEvent("2019tur_qm16"));
        assertEquals(4, EventMap.getInstance().getMatchFromEvent("2019tur_qm1").getBlueScore());
        assertEquals(1, EventMap.getInstance().getMatchFromEvent("2019tur_qm1").getRedScore());
        assertEquals(6, EventMap.getInstance().getMatchFromEvent("2019tur_qm16").getBlueScore());
        assertEquals(0, EventMap.getInstance().getMatchFromEvent("2019tur_qm16").getRedScore());
        assertEquals(4, QualMatchDatabaseManager.getInstance().getScore("2019tur_qm1").blueScore());
        assertEquals(1, QualMatchDatabaseManager.getInstance().getScore("2019tur_qm1").redScore());
        assertEquals(6, QualMatchDatabaseManager.getInstance().getScore("2019tur_qm16").blueScore());
        assertEquals(0, QualMatchDatabaseManager.getInstance().getScore("2019tur_qm16").redScore());
    }

    @Test
    public void testCorrectPriority(){
        EventMap.getInstance();
        for(int i = 1; i <= 16; i++){
            QualMatchScorer.getInstance().addMatchToScore(EventMap.getInstance().getMatchFromEvent("2019tur_qm" + i));
        }
        for(int i = 1; i <= 16; i++){
            assertEquals("2019tur_qm" + i, QualMatchScorer.getInstance().pollMatch().getKey());
        }
    }

}
