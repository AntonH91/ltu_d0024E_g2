package dbbg2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database defaultInstance;

    private Connection conn;

    private static final String DATABASE_URL = "jdbc:mysql://localhost/library_dbb?serverTimezone=UTC";
    private static final String DATABASE_USER = "library_dbb_application";
    private static final String DATABASE_PWD = "ldbbapp";


    /**
     * Initializes a new database manager.
     * @param url The URL of the database, as used by JDBC
     * @param user The username of the database
     * @param pwd The password of the database
     * @throws SQLException Thrown in case something goes wrong.
     */

    public Database(String url, String user, String pwd) throws SQLException {
        conn = DriverManager.getConnection(DATABASE_URL,DATABASE_USER,DATABASE_PWD);
    }

    /**
     * Gets the default instance of the database manager.
     * @return The default instance
     */
    public static Database getDefaultInstance() throws SQLException {
        if (defaultInstance == null) {
            defaultInstance = new Database(DATABASE_URL,DATABASE_USER, DATABASE_PWD);
        }
        return defaultInstance;
    }


    public Connection getConnection() {
        return conn;
    }

    public static void main(String[] args) {
        Database db;

        try {
            db = Database.getDefaultInstance();
            System.out.println("Connection successful!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }


}
