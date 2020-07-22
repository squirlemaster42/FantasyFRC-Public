package com.fantasyfrc.scoring;

import com.fantasyfrc.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchDatabaseManager {

    private static MatchDatabaseManager instance;

    public static MatchDatabaseManager getInstance(){
        if(instance == null){
            instance = new MatchDatabaseManager();
        }
        return instance;
    }

    //SQL information
    private final String username, password, url;
    record ScoreRecord(int redScore, int blueScore){}

    private MatchDatabaseManager(){
        username = Constants.getInstance().getConfig("sql").getProperty("username");
        password = Constants.getInstance().getConfig("sql").getProperty("password");
        url = Constants.getInstance().getConfig("sql").getProperty("url");
    }

    Connection getCon(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect!");
        }
        return null;
    }

    public ScoreRecord getScore(final String matchId){
        try(Statement statement = getCon().createStatement()){
            ResultSet rs = statement.executeQuery(String.format("SELECT * from qualMatches WHERE id = '%s'", matchId));
            if(!rs.next()){
                return new ScoreRecord(-1, -1);
            }else{
                return new ScoreRecord(rs.getInt("red_score"), rs.getInt("blue_score"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return new ScoreRecord(-1, -1);
    }

    public void updateScore(final String matchId, final int redScore, final int blueScore){
        try(Statement statement = getCon().createStatement()){
            ResultSet exists = statement.executeQuery(String.format("SELECT * from qualMatches WHERE id = '%s'", matchId));
            if(exists.first()){
                //Team exists, we need to update
                statement.execute(String.format("UPDATE qualMatches set red_score = %d WHERE id = '%s'", redScore, matchId));
                statement.execute(String.format("UPDATE qualMatches set blue_score = %d WHERE id = '%s'", blueScore, matchId));
            }else{
                //Team does not exist, we need to add
                statement.execute(String.format("INSERT INTO qualMatches VALUES('%s', %d, %d)", matchId, redScore, blueScore));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Get scores from match
    //Add match to database
    //Update match if it already exists
    //Figure out if match has been replayed and needs to be updated
}
