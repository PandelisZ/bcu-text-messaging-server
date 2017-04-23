package msgServer;

import java.sql.*;


/**
 * Interfaces with the remote MySQL database and makes sure all tables are created as needed
 */
public class Database {

    private final String dbURI;
    private final String dbUser;
    private final String dbPass;
    private Connection conn = null;

    /**
     * loaded driver into the memory, so it can be utilized as an implementation of the JDBC interfaces.
     */

    public Database(String URI, String username, String password){
        this.dbURI = URI;
        this.dbUser = username;
        this.dbPass = password;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
    }

    /**
     * For querying server only. Can not ALTER data
     * @param sql the SQL query to send
     * @return returns a ResultSet containing all the returned data
     */

    public ResultSet executeSQLQuery(String sql){
        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * For altering data only
     * @param sql The SQL query
     */
    public void executeSQLUpdate(String sql){
        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return the connection object for the database
     */
    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(dbURI, dbUser, dbPass);
            return conn;
        } catch (SQLException e) {
            System.out.println("Error reading database");
        }
        return null;
    }
}



