package com.mycompany.app;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabasePool {

    private String databaseHost;
    private String databaseUser;
    private String databasePassword;
    private String databaseName;

    public DatabasePool(FileReader reader) throws IOException {
        Properties p = new Properties();
        p.load(reader);
        this.databaseHost = p.getProperty("DATABASE_HOST");
        this.databaseUser = p.getProperty("DATABASE_USER");
        this.databasePassword = p.getProperty("DATABASE_PASS");
        this.databaseName = p.getProperty("DATABASE_NAME");
    }

    public Connection getConnection() throws SQLException{

        String url = String.format("jdbc:postgresql://%s/%s?user=%s&password=%s", 
                                                        databaseHost, 
                                                        databaseName, 
                                                        databaseUser, 
                                                        databasePassword);
        return DriverManager.getConnection(url);
    }
}