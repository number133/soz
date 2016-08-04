package kz.soz.dic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 04.08.2016 17:20
 * Copyright © LLP JazzSoft
 */
public class DatabaseWriter {
    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "org.postgresql.Driver";
    private final String DB_URL = "jdbc:postgresql://localhost:5432/kzdic";

    //  Database credentials
    private final String USER = "postgres";
    private final String PASS = "postgres";

    private Connection conn = null;

    public DatabaseWriter() {
        try{
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            Statement stmt = conn.createStatement();

            String tableSql = "CREATE TABLE dictionary(\n" +
                    "   id SERIAL PRIMARY KEY     NOT NULL,\n" +
                    "   kz           TEXT    NOT NULL,\n" +
                    "   ru           TEXT    NOT NULL \n" +
                    ");";
            String indexSql = "CREATE INDEX inx_dictionary_kz ON dictionary (kz);";
            stmt.executeUpdate(tableSql);
            stmt.executeUpdate(indexSql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void write(String word, String translation) {
        Statement stmt = null;
        try{
            stmt = conn.createStatement();

            String sql = "INSERT INTO dictionary(kz, ru) " +
                    "VALUES ('" + word.toLowerCase() + "','" + translation.toLowerCase() + "')";
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
