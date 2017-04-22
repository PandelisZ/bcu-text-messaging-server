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

        String user = in.readLine();
        String password = in.readLine();
        String dob = in.readLine();
        String tel = in.readLine();
        String add = in.readLine();

       if(conn.getCurrentUser() == null){
           System.out.println("TEST");
           if(conn.getServer().isValidUser(user) == false) { // make sure the user doesn't exist
               String sqlInsertUser =  "INSERT INTO `users` (`user`, `pass`, `dob`, `tel`, `add`) VALUES ('"+user+"','"+password+"', STR_TO_DATE('"+dob+"', '%Y-%m-%d'),'"+tel+"','"+add+"')";
               conn.getServer().getDatabase().executeSQLUpdate(sqlInsertUser);
               out.write("200\r\n");
               out.flush();
           }else{
               (new ErrorCommand(in, out, conn, "That user already exists")).execute();
           }
       }
       else{
           (new ErrorCommand(in, out, conn, "You're already logged in, no need to register")).execute();
       }



    }
}
