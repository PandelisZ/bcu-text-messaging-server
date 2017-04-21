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

       FileWriter fileOut = new FileWriter("TestFile1.txt", true);
       fileOut.write(user);
       fileOut.write(password);
       fileOut.write(dob);
       fileOut.write(tel);
       fileOut.write(add);

       fileOut.close();

       out.write("200\r\n");

    }
}
