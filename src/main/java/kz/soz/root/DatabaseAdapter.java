package kz.soz.root;

import java.sql.*;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 04.08.2016 17:59
 * Copyright © LLP JazzSoft
 */
public class DatabaseAdapter {
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "org.postgresql.Driver";
    private final String DB_URL = "jdbc:postgresql://localhost:5432/kzdic";

    //  Database credentials
    private final String USER = "postgres";
    private final String PASS = "postgres";

    private Connection conn = null;

    public DatabaseAdapter() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getTranslation(String word) {
        Statement stmt = null;
        try{
            stmt = conn.createStatement();

            String sql = "select ru from dictionary where kz = '" + word + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            if(resultSet.next()) {
                return resultSet.getString("ru");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
