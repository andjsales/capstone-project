// the gateway between Java and MySQL

package com.example.progress.connection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    private static Connection connection;

    private ConnectionManager() {}

    public static void makeConnection()
            throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();

        InputStream fis =
                ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties");
        if (fis == null) {
            throw new FileNotFoundException("config.properties not found in classpath!");
        }

        props.load(fis);

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(url, username, password);
    }

    public static Connection getConnection()
            throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        if (ConnectionManager.connection == null) {
            makeConnection();
        }
        return connection;
    }
}

