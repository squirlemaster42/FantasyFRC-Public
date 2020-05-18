package com.fantasyfrc.draft;

import com.fantasyfrc.utils.Constants;
import com.fantasyfrc.utils.DataProvider;

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
    private final DataProvider<String, Draft> dataProvider;

    private DraftDatabaseManager(){
        dataProvider = new DraftProvider();
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

    //TODO Test
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

    void addDraft(Draft d) throws SQLException {
        Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
        String sqlStr = String.format("insert into drafts values ('%s', '%s');", d.getID(), d.genJSONStr());
        DraftDatabaseManager.getInstance().write(statement, sqlStr);
    }

    void updateDatabase(Draft d){
        try {
            Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
            String sqlStr = String.format("update drafts set drafts = '%s' where id = '%s';", d.genJSONStr(), d.getID());
            DraftDatabaseManager.getInstance().write(statement, sqlStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DataProvider<String, Draft> getDataProvider() {
        return dataProvider;
    }

    private static class DraftProvider implements DataProvider<String, Draft>{

        @Override
        public Draft get(String key) {
            try {
                Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
                String result = DraftDatabaseManager.getInstance().read(statement, String.format("select * from drafts where id = \"%s\";", key));
                return Constants.getInstance().getGson().fromJson(result, Draft.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
