package msgServer;

import java.sql.*;


/**
 * Interfaces with the remote MySQL database and makes sure all tables are created as needed
 */
public class Database {

    //final String dbURI = "jdbc:mysql://bcu-texting-coursework-cluster-1.cluster-cueefshnasyf.eu-west-2.rds.amazonaws.com:3306/texting-test";
    //final String dbUser = "testing";
    //final String dbPass = "ry0RJN7aYL1Q5EB9dmEQpb0";

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

    public void executeSQLUpdate(String sql){
        Connection conn = getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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



