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
    record AllianceScore(String teams, int score){}

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

    public AllianceScore getScore(final String teams){
        try(Statement statement = getCon().createStatement()){
            ResultSet rs = statement.executeQuery(String.format("Select * from playoffs where teams  = '%s'", teams));
            if(!rs.next()){
                return new AllianceScore(null, -1); //Sent if result is bad
            }else{
                return new AllianceScore(teams, rs.getInt("score"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new AllianceScore(null, 0);
    }

    public void updateScore(final AllianceScore allianceScore){
        try(Statement statement = getCon().createStatement()){
            ResultSet exists = statement.executeQuery(String.format("SELECT * from playoffs where teams = '%s'", allianceScore.teams));
            if(exists.first()){
                statement.execute(String.format("UPDATE playoffs set score = '%d' WHERE teams = '%s'", allianceScore.score, allianceScore.teams));
            }else{
                statement.execute(String.format("INSERT INTO playoffs VALUES('%s', '%d')", allianceScore.teams, allianceScore.score));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
