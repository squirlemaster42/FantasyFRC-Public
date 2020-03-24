package com.fantasyfrc.draft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DraftDatabaseManager {

    private static DraftDatabaseManager instance;

    public static DraftDatabaseManager getInstance(){
        if(instance == null){
            instance = new DraftDatabaseManager();
        }
        return instance;
    }

    private final String username = "root";
    private final String password = "password";
    private final String url = "jdbc:mysql://localhost:3306/users";

    private DraftDatabaseManager(){
        getCon();
    }

    protected void reconnect(){
        getCon();
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

    //This read is bad, convert to use lambda or something else
    //Only gets first line
    String read(final Statement statement, final String sqlString){
        try {
            ResultSet result = statement.executeQuery(sqlString);
            if(result.first()){
                return result.getString("drafts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    void loadDrafts(final Statement statement, final String sqlString){
        try {
            ResultSet resultSet = statement.executeQuery(sqlString);
            while(resultSet.next()){
                new Draft(resultSet.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void write(final Statement statement, final String sqlString){
        try {
            statement.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    boolean draftExists(final String id){
        try {
            Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
            ResultSet result = statement.executeQuery("select id from drafts where id = '" + id + "'");
            return result.first();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
