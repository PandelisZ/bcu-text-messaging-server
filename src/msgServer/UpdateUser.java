package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Jorda on 21/04/2017.
 */
public class UpdateUser implements Command{

    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;




    public UpdateUser(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {

        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }
    public void execute() throws IOException {
        /**
         * @param user the users name that is currently logged in
         *
         */

        String user = in.readLine();
        String password = in.readLine();
        String dob = in.readLine();
        String tel = in.readLine();
        String add = in.readLine();
        /**
         * checks if the user designed for change is the current user logged in
         * if user  is logged in and selects themself the data can be changed
         */
        if(user != null && user.equals(conn.getCurrentUser())) {
            /**
             *@param sqlUpdateUser sets the SQL statement to update the users details
             *@param conn.getServer().getDatabase.executeSQLUpdate(sqlUpdateUser) sends command to server.
             */
            String sqlUpdateUser =  "UPDATE `users` SET `pass` = '"+ password + "', `dob` = STR_TO_DATE('"+dob+"', '%Y-%m-%d')"+ ", `tel` = '"+ tel +"', `add` = '"+add+"' WHERE `user` = '"+user+"';";
            conn.getServer().getDatabase().executeSQLUpdate(sqlUpdateUser);
            out.write("200\r\n");
            out.flush();
        }else{
            (new ErrorCommand(in, out, conn, "You are not the user logged on")).execute();
        }






    }


}
