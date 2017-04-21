package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
        String password = null;
        String dob = null;
        String tel = null;
        String add = null;
        /**
         * checks if the user designed for change is the current user logged in
         * if user  is logged in and selects themself the data can be changed
         */
        if(user != null && user.equals(conn.getCurrentUser())) {
            user = in.readLine();
            password = in.readLine();
            dob = in.readLine();
            tel = in.readLine();
            add = in.readLine();
        }

        /**
         * @param fileout  used for testing purposes to see if the data can be written to a file and updated.
         */
        FileWriter fileout = new FileWriter("registration.txt",true);

        fileout.write(user);
        fileout.write(password);
        fileout.write(dob);
        fileout.write(tel);
        fileout.write(add);
        fileout.close();

        out.write("200\r\n");


    }


}
