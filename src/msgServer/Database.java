package msgServer;

import java.sql.*;


/**
 * Interfaces with the remote MySQL database and makes sure all tables are created as needed
 */
public class Database {

    final String dbURI = "jdbc:mysql://bcu-texting-coursework-cluster-1.cluster-cueefshnasyf.eu-west-2.rds.amazonaws.com:3306/texting-test";
    final String dbUser = "testing";
    final String dbPass = "ry0RJN7aYL1Q5EB9dmEQpb0";

    /**
     * loaded driver into the memory, so it can be utilized as an implementation of the JDBC interfaces.
     */
    protected void registerDriver() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
    }

    {
        this.registerDriver();
        try {
            Connection conn = DriverManager.getConnection(dbURI, dbUser, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

//    protected void finalize() throws Throwable {
//        try {
//            this.conn.close(); //Explicitly closing a connection conserves DBMS resources, which will make your database administrator happy.
//        } finally {
//            super.finalize();
//        }
//    }


}
