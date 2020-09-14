package com.fantasyfrc.draft;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DraftDatabaseManagerTest {

    @Test
    public void testDraftExists(){
        assertTrue(DraftDatabaseManager.getInstance().draftExists("testDraft"));
        //No draft should exist with this name
        assertFalse(DraftDatabaseManager.getInstance().draftExists("."));
    }
}
