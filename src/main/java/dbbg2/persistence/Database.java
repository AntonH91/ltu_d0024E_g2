package dbbg2.persistence;

import java.sql.*;

/**
 * This class manages the innner workings of the database and encapsulates the global database connection used by the application.
 *
 * @Author Anton Högelin (anthge-7)
 */
public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/library_dbb?serverTimezone=UTC";
    private static final String DATABASE_USER = "library_dbb_application";
    private static final String DATABASE_PWD = "ldbbapp";
    private static Database defaultInstance;
    private final Connection conn;


    /**
     * Initializes a new database manager.
     *
     * @param url  The URL of the database, as used by JDBC
     * @param user The username of the database
     * @param pwd  The password of the database
     * @throws SQLException Thrown in case something goes wrong.
     */

    public Database(String url, String user, String pwd) throws SQLException {
        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
    }

    /**
     * Gets the default instance of the database manager.
     *
     * @return The default instance
     */
    public static Database getDefaultInstance() throws SQLException {
        if (defaultInstance == null) {
            defaultInstance = new Database(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
        }
        return defaultInstance;
    }

    /**
     * Purely for testing
     *
     * @param args TODO Remove this, really.
     */
    public static void main(String[] args) {
        Database db;

        try {
            db = Database.getDefaultInstance();
            System.out.println("Connection successful!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /**
     * Gets a Statement object from the active connection
     *
     * @return A statement object that can be used for querying the database
     * @throws SQLException Thrown when an SQL error occurs
     */
    public Statement getStatement() throws SQLException {
        return conn.createStatement();
    }

    /**
     * Prepares a Statement based on the input SQL
     *
     * @param sql The query to prepare
     * @return A PreparedStatement with the given SQL
     * @throws SQLException Thrown when an SQL error occurs
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    /**
     * Prepares a Statement based on the input SQL
     *
     * @param sql               The query to prepare
     * @param autoGeneratedKeys A Statement constant describing the autoGeneratedKeys behaviour
     * @return A PreparedStatement with the given SQL
     * @throws SQLException Thrown when an SQL error occurs
     */
    public PreparedStatement getPreparedStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return conn.prepareStatement(sql, autoGeneratedKeys);
    }

    /**
     * Prepares a procedure call
     *
     * @param sql The procedure call definition
     * @return A CallableStatement based on the given SQL
     * @throws SQLException Thrown when an SQL error occurs
     */
    public CallableStatement getCallableStatement(String sql) throws SQLException {
        return conn.prepareCall(sql);
    }

    /**
     * Gets the current AutoCommit setting
     *
     * @return True if autoCommit is enabled, false otherwise
     * @throws SQLException Thrown when SQL errors occur
     */
    public boolean getAutoCommit() throws SQLException {
        return conn.getAutoCommit();
    }

    /**
     * Sets the autoCommit mode of the connection object, to disable direct control of transaction grouping.
     *
     * @param autoCommit True to enable automatic commits on statement execution, false to disable
     * @throws SQLException Thrown when SQL errors occur
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    /**
     * Alias for setAutoCommit(false)
     *
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        this.setAutoCommit(false);
    }

    /**
     * Commits the currently open transaction.
     *
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException {
        conn.commit();
    }

    /**
     * Rolls back the currently open transaction.
     *
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        conn.rollback();
    }


}
