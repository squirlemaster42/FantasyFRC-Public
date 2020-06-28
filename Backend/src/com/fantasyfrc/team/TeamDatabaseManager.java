package com.fantasyfrc.team;

import com.fantasyfrc.utils.Constants;

import java.sql.*;

public class TeamDatabaseManager {

    //TODO Store matches that teams are in

    private static TeamDatabaseManager instance;

    public static TeamDatabaseManager getInstance() {
        if(instance == null){
            instance = new TeamDatabaseManager();
        }
        return instance;
    }

    //SQL information
    private final String username, password, url;

    private TeamDatabaseManager(){
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

    /**
     * Gets the score for a team from the database
     * @param teamId The id of the team of access
     * @return The score of the team, will return -1 if the team does not exist
     */
    public int getScore(final String teamId){
        try(Statement statement = getCon().createStatement()){
            ResultSet rs = statement.executeQuery(String.format("SELECT * from teams WHERE id = '%s'", teamId));
            if(!rs.next()){
                return -1;
            }else{
                return rs.getInt("score");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Updates the score for a team in the database
     * @param teamId The id of the team to update
     * @param score The new score for the team
     */
    public void updateScore(final String teamId, final int score){
        try(Statement statement = getCon().createStatement()){
            ResultSet exists = statement.executeQuery(String.format("SELECT * from teams WHERE id = '%s'", teamId));
            if(exists.first()){
                //Team exists, we need to update
                statement.execute(String.format("UPDATE teams set score = %d WHERE id = '%s'", score, teamId));
            }else{
                //Team does not exist, we need to add
                statement.execute(String.format("INSERT INTO teams VALUES('%s', %d)", teamId, score));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
