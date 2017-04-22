package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 * Created by Matas on 4/21/2017.
 */
public class Registration implements Command {

    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;

    public Registration(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {

        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }
   public void execute() throws IOException {
/**
 * the code below records user inputs when attempting to register
 * these can then be used by the sql statement to make additions to the database
 */
        String user = in.readLine();
        String password = in.readLine();
        String dob = in.readLine();
        String tel = in.readLine();
        String add = in.readLine();
        String sqlInsertUser;



       if(conn.getCurrentUser() == null) {

/**

@param sqlnstertUser
 this code is used to add a user into the database if it does not already exist
**/
           if(conn.getServer().isValidUser(user) == false) {
               sqlInsertUser = "INSERT INTO `users` (`user`, `pass`, `dob`, `tel`, `add`) VALUES ('" + user + "','" + password + "', STR_TO_DATE('" + dob + "', '%Y-%m-%d'),'" + tel + "','" + add + "')";
               conn.getServer().getDatabase().executeSQLUpdate(sqlInsertUser);

               out.write("200\r\n");
               out.flush();
           }

           /**
            * The code below ensures that multiple users with identical usernames cannot be added to the database
            * An error is thrown if an attempt is made to do this
            */
           else {
               (new ErrorCommand(in, out, conn, "User already with that name already exists")).execute();

           }
           }
       /**
        * An error will be thrown if a user attempts to access the registration protocol while already logged into an account
        */
       else {
           (new ErrorCommand(in, out, conn, "You cannot register while logged into an account.")).execute();


       }
   }
}




