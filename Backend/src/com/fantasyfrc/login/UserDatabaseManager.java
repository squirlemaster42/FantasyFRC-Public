package com.fantasyfrc.login;

import com.fantasyfrc.draft.Draft;
import com.fantasyfrc.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

//TODO Refactor this
public class UserDatabaseManager {

    private static UserDatabaseManager instance;

    public static UserDatabaseManager getInstance() {
        if (instance == null) {
            instance = new UserDatabaseManager();
        }
        return instance;
    }

    private final String username = "root";
    private final String password = "password";
    private final String url = "jdbc:mysql://localhost:3306/users";

    private UserDatabaseManager() {
        getCon();
    }

    protected void reconnect() {
        getCon();
    }

    private Connection getCon() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect!");
        }
        return null;
    }


    public boolean doesUserExists (final String username){
        Statement statement = null;
        try {
            statement = Objects.requireNonNull(getCon()).createStatement();
            ResultSet result = statement.executeQuery("select username from users where username = '" + username + "'");
            return result.first();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUserInDB(final User user){
        Statement statement = null;
        try {
            statement = Objects.requireNonNull(getCon()).createStatement();
            statement.executeUpdate("update users set drafts = \'" + user.genJsonString() + "\' where username = \'" + user.getUsername() + "\'");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getDraftsForUser(final String userID){
        try {
            Statement statement = Objects.requireNonNull(getCon()).createStatement();
            ResultSet result = statement.executeQuery("select drafts from users where username = '" + userID + "'");
            if(result.first()){
                return result.getString("drafts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void writeUser(final String username, final String password){
        try {
            new SQLWriteObject(username, password).writeObject(Objects.requireNonNull(getCon()).createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String readUser(final String username){
        try {
            return new SQLReadObject(username).readObject(Objects.requireNonNull(getCon()).createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    private class SQLReadObject{

        private final String sqlString;
        private String password;

        SQLReadObject(final String username){
            sqlString = "SELECT password from users WHERE username = '" + username + "'";
        }

        String readObject(final Statement statement){
            try {
                ResultSet result = statement.executeQuery(sqlString);
                if(result.first()){
                    password = result.getString("password");
                    return password;
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SQLWriteObject{

        private final String sqlString;

        SQLWriteObject(final String username, final String password){
            sqlString = "INSERT INTO users(username, password) values('" + username + "', '" + password + "')";
        }

        void writeObject(final Statement statement){
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
    }

}
