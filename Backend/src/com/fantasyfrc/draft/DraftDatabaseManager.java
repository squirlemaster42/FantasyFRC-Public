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

    private final String username, password, url;
    private final DataProvider<String, Draft> dataProvider;

    private DraftDatabaseManager(){
        username = Constants.getInstance().getConfig("sql").getProperty("username");
        password = Constants.getInstance().getConfig("sql").getProperty("password");
        url = Constants.getInstance().getConfig("sql").getProperty("url");
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
        String sqlStr = String.format("insert into drafts values ('%s', '%s');", d.getId(), d.genJSONStr());
        DraftDatabaseManager.getInstance().write(statement, sqlStr);
    }

    void updateDatabase(Draft d){
        try {
            if(draftExists(d.getId())){
                Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
                String sqlStr = String.format("update drafts set drafts = '%s' where id = '%s';", d.genJSONStr(), d.getId());
                DraftDatabaseManager.getInstance().write(statement, sqlStr);
            }else{
                Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon()).createStatement();
                String sqlStr = String.format("insert into drafts values ('%s', '%s');", d.getId(), d.genJSONStr());
                DraftDatabaseManager.getInstance().write(statement, sqlStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Draft loadDraft(final String id){
        if(draftExists(id)){
            try {
                Statement statement = Objects.requireNonNull(DraftDatabaseManager.getInstance().getCon().createStatement());
                String draftStr = DraftDatabaseManager.getInstance().read(statement, String.format("select drafts from drafts where id = \"%s\"", id));
                return Constants.getInstance().getGson().fromJson(draftStr, Draft.class);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }else{
            return null;
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
