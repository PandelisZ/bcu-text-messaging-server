package msgServer;

import javax.sql.rowset.CachedRowSet;
import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.sql.*;

/**
 * A class to model the message server itself
 */
public class MessageServer {
    public static final int DEFAULT_PORT = 9801;
    private int port;
    private MessageCollection messages;
    private Database mysqlDatabase;
    private boolean verbose;

    /**
     * Construct a new MessageServer
     *
     * @param int portNumber The port number on which the server will listen.
     *            The default port number is 9801
     */
    public MessageServer(int portNumber) throws IOException {
        // save the port number
        port = portNumber;
        // set up a FileInputStream which will be used to read in the user details
        FileInputStream fin = null;
        // Construct a new (empty) MessageCollection
        messages = new MessageCollection();
        // Set up database connection for login and everything else
        mysqlDatabase = new Database("jdbc:mysql://bcu-texting-coursework-cluster-1.cluster-cueefshnasyf.eu-west-2.rds.amazonaws.com:3306/texting-test",
                "bcutexting", "7Bfu6sNx28U32vLtOPLQ6QI");
    }

    public Database getDatabase(){
        return mysqlDatabase;
    }

    /**
     * Construct a new MessageServer using the default port of 9801
     */
    public MessageServer() throws IOException {
        this(DEFAULT_PORT);
    }

    /**
     * Start the server
     */
    public void startService() {
        ServerSocket serverSocket = null;
        Socket clientConnection = null;
        try {
            // print out some information to the user
            userMsg("MessageServer: Starting message service on port " + port);
            // Create the server socket
            serverSocket = new ServerSocket(port);
            while (true) {
                // wait for the next connection
                clientConnection = serverSocket.accept();
                userMsg("MessageServer: Accepted from " +
                        clientConnection.getInetAddress());
                // Create a new thread to handle this connection
                MsgSvrConnection conn =
                        new MsgSvrConnection(clientConnection, this);
                // if you require some information about what is going on
                // pass true to setVerbose.
                // If you're tired of all those messages, pass false to turn them off
                conn.setVerbose(true);
                // Start the new thread
                conn.start();
                // now loop around to await the next connection
                // no need to wait for the thread to finish
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            // We will close the server now
            userMsg("Message Server closing down");
            try {
                if (clientConnection != null) {
                    clientConnection.close();
                }
            } catch (IOException e) {
            }
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    try {
                        mysqlDatabase.getConnection().close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
            }
        }

    }


    /**
     * Start the program.<br>
     * Usage: java MessageServer [portNumber].
     *
     * @params String[] args The only argument is an optional port number
     */
    public static void main(String[] args) throws IOException {
        int portNumber;
        if (args.length == 1) {
            // get the port number we were given
            portNumber = Integer.parseInt(args[0]);
        } else {
            // use the default port number
            portNumber = DEFAULT_PORT;
        }
        // create a new MessageServer using this port number
        MessageServer server = new MessageServer(portNumber);
        // set verbose to true if you want information
        // set it to false to turn off all messages
        server.setVerbose(true);
        // Now start the messaging service
        server.startService();
    }

    /**
     * Query to obtain the message collection from the server.
     *
     * @return MessageCollection The collection of messages that are
     * waiting to be delivered to the users.
     */
    public MessageCollection getMessages() {
        return messages;
    }

    /**
     * Query to get the password for a specific user.
     *
     * @param String user The username of the user whose password we want to know
     * @return String the password of this user
     */
    public String getUserPassword(String user) {
        ResultSet rs = mysqlDatabase.executeSQLQuery("SELECT pass from users WHERE user = '" + user + "'");
        try {
            if(rs.next()){
                return rs.getString("pass");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Query to find out if a username represents a valid user.  If the username
     * is in the password file, return true, else return false
     *
     * @return boolean True if the user is in the password file, false otherwise
     */
    public boolean isValidUser(String username) {
        ResultSet rs = mysqlDatabase.executeSQLQuery("SELECT user from users WHERE user = '" + username + "'");
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the value of the verbose flag which determines whether or not
     * logging is turned on.
     *
     * @return boolean The current value of the verbose flag
     */
    public boolean getVerbose() {
        return verbose;
    }

    /**
     * Set the verbose flag
     *
     * @param boolean verbose The new value for the verbose flag
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Print out a message to the user dependent on the value of the verbose flag
     */
    private void userMsg(String msg) {
        if (verbose) {
            System.out.println("MessageServer: " + msg);
        }
    }
}
