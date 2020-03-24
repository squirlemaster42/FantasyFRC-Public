package com.fantasyfrc.draft;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class DraftMap {

    private static DraftMap instance;

    public static DraftMap getInstance() {
        if(instance == null){
            instance = new DraftMap();
        }
        return instance;
    }

    private final Map<String, Draft> draftMap;

    private DraftMap(){
        draftMap = new ConcurrentHashMap<>();
    }

    public void start(){
        try {
            Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
            DraftDatabaseManager.getInstance().loadDrafts(statement, "select * from drafts;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void addDraft(final String id, final Draft draft){
        draftMap.put(id, draft);
    }

    public Draft getDraft(final String id){
        return draftMap.get(id);
    }
}
