// the gateway between Java and MySQL

package com.example.progress.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

    private ConnectionManager() {}

    public static Connection getConnection()
            throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();

        try (InputStream input =
                ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties not found in classpath!");
            }
            props.load(input);
        }

        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);

    }
}
