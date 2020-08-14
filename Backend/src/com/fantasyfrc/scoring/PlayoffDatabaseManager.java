package com.fantasyfrc.scoring;

import com.fantasyfrc.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayoffDatabaseManager {

    public static PlayoffDatabaseManager instance;

    public static PlayoffDatabaseManager getInstance(){
        if(instance == null){
            instance = new PlayoffDatabaseManager();
        }
        return instance;
    }

    private final String username, password, url;
    record AllianceScore(String[] teams, int score){}
    record TeamScore(String team, int score){}

    private PlayoffDatabaseManager(){
        username = Constants.getInstance().getConfig("sql").getProperty("username");
        password = Constants.getInstance().getConfig("sql").getProperty("password");
        url = Constants.getInstance().getConfig("sql").getProperty("url");
    }

    Connection getCon(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO Need to fix to add event
    public TeamScore getScore(final String team, final String event){
        try(Statement statement = getCon().createStatement()){
            ResultSet rs = statement.executeQuery(String.format("Select * from playoffs where team  = '%s' and event = '%s'", team, event));
            if(!rs.next()){
                return new TeamScore(null, -1); //Sent if result is bad
            }else{
                return new TeamScore(team, rs.getInt("score"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new TeamScore(null, 0);
    }

    public void updateScore(final AllianceScore allianceScore, final String event){
        try(Statement statement = getCon().createStatement()){
            //If one team exists the rest should
            ResultSet exists = statement.executeQuery(String.format("SELECT * from playoffs where team = '%s' and event = '%s'", allianceScore.teams[0], event));
            if(exists.first()){
                for(String team : allianceScore.teams){
                    statement.execute(String.format("UPDATE playoffs set score = '%d' WHERE team = '%s' and event  = '%s'", allianceScore.score, team, event));
                }
            }else{
                for(String team : allianceScore.teams) {
                    statement.execute(String.format("INSERT INTO playoffs VALUES('%s', '%s', '%d')", team, event, allianceScore.score));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
