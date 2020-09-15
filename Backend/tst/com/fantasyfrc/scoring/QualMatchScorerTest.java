package com.fantasyfrc.scoring;

import com.fantasyfrc.event.EventMap;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class QualMatchScorerTest {

    @Test
    public void testScoreMatch(){
        //TODO Need to do threaded test
        EventMap.getInstance();
        try {
            EventMap.getInstance().addEvent("2020txpla");
        } catch (IOException e) {
            e.printStackTrace();
        }
        QualMatchScorer.getInstance().scoreMatch(EventMap.getInstance().getMatchFromEvent("2020txpla_qm1"));
        QualMatchScorer.getInstance().scoreMatch(EventMap.getInstance().getMatchFromEvent("2020txpla_qm30"));
        assertEquals(0, EventMap.getInstance().getMatchFromEvent("2020txpla_qm1").getBlueScore());
        assertEquals(3, EventMap.getInstance().getMatchFromEvent("2020txpla_qm1").getRedScore());
        assertEquals(0, EventMap.getInstance().getMatchFromEvent("2020txpla_qm30").getBlueScore());
        assertEquals(4, EventMap.getInstance().getMatchFromEvent("2020txpla_qm30").getRedScore());
        assertEquals(0, QualMatchDatabaseManager.getInstance().getScore("2020txpla_qm1").blueScore());
        assertEquals(3, QualMatchDatabaseManager.getInstance().getScore("2020txpla_qm1").redScore());
        assertEquals(0, QualMatchDatabaseManager.getInstance().getScore("2020txpla_qm30").blueScore());
        assertEquals(4, QualMatchDatabaseManager.getInstance().getScore("2020txpla_qm30").redScore());
    }

    @Test
    public void testCorrectPriority(){
        EventMap.getInstance();
        try {
            EventMap.getInstance().addEvent("2020txpla");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 1; i <= 16; i++){
            QualMatchScorer.getInstance().addMatchToScore(EventMap.getInstance().getMatchFromEvent("2020txpla_qm" + i));
        }
        for(int i = 1; i <= 16; i++){
            assertEquals("2020txpla_qm" + i, QualMatchScorer.getInstance().pollMatch().getKey());
        }
    }

}
