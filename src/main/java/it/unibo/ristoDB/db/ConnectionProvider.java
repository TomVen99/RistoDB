package it.unibo.ristoDB.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Expose a utility method to connect to a MySQL database.
 */
public final class ConnectionProvider {
    private final String username;
    private final String password;
    private final String dbName;

    /**
     * @param username the username used to connect to the database
     * @param password the password used to connect to the database
     * @param dbName the name of the database to connect to
     */
    public ConnectionProvider(final String username, final String password, final String dbName) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    /**
     * @return a Connection with the database specified in the class constructor
     * @throws IllegalStateException if the connection could not be establish
     */
    public Connection getMySQLConnection() {
        final String dbUri = "jdbc:mysql://127.0.0.1:3306/" + this.dbName +"?serverTimezone=Europe/Rome";
        try {
            System.out.println(dbUri);
            System.out.println(username);
            System.out.println(password);
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(dbUri, this.username, this.password);
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalStateException("Could not establish a connection with db", e);
        }
    }
}
