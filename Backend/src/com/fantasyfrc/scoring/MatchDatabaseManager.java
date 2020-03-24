package com.fantasyfrc.scoring;

import java.sql.*;

public class MatchDatabaseManager {

    private static MatchDatabaseManager instance;

    public static MatchDatabaseManager getInstance(){
        if(instance == null){
            instance = new MatchDatabaseManager();
        }
        return instance;
    }

    //TODO Centralize SQL information
    //SQL information
    private final String username = "root";
    private final String password = "password";
    private final String url = "jdbc:mysql://localhost:3306/users";

    private MatchDatabaseManager(){}

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

    //TODO Figure out how to make this better, int[] is kinda ugly
    public int[] getScore(final String matchId){
        try(Statement statement = getCon().createStatement()){
            ResultSet rs = statement.executeQuery(String.format("SELECT * from matches WHERE id = '%s'", matchId));
            if(!rs.next()){
                return new int[]{-1, -1};
            }else{
                return new int[]{rs.getInt("red_score"), rs.getInt("blue_score")};
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return new int[]{-1, -1};
    }

    public void updateScore(final String matchId, final int redScore, final int blueScore){
        try(Statement statement = getCon().createStatement()){
            ResultSet exists = statement.executeQuery(String.format("SELECT * from matches WHERE id = '%s'", matchId));
            if(exists.first()){
                //Team exists, we need to update
                statement.execute(String.format("UPDATE matches set red_score = %d WHERE id = '%s'", redScore, matchId));
                statement.execute(String.format("UPDATE matches set blue_score = %d WHERE id = '%s'", blueScore, matchId));
            }else{
                //Team does not exist, we need to add
                statement.execute(String.format("INSERT INTO matches VALUES('%s', %d, %d)", matchId, redScore, blueScore));
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
