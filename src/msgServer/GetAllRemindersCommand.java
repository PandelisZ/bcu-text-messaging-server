package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GetAllRemindersCommand implements Command {
    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;

    public GetAllRemindersCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }

    public void execute() throws IOException {
        //declare a variable user of type String and use it to get the user from the inputstream
        String user = in.readLine();
        //check if current user is not equal to null and current user is equal to the user (use the method getCurrentUser())
        if(user != null && user.equals(conn.getCurrentUser())) {
            //intialise an array (msgs) that is used to hold all the messages read and set it's initialised value to null
            Reminder[] r = null;
            //use the method getAllMessages(user) to populate the msgs array
            ReminderCollection reminders = conn.getServer().getReminders();
            r = reminders.getAllReminders(user);
            //check if msgs is not equal to null
            if(r != null){
                //write to the OutputStream the message status code as specified in the protocol
                out.write("201" + "\r\n");
                //write the length of the messages returned
                out.write(r.length + "\r\n");
                //Loop through the messages and write the sender, date and cotent to the outputstream (use provided methods)
                for (Reminder rem : r){
                    out.write(rem.getId() + "\r\n");
                    out.write(rem.getDate() + "\r\n");
                    out.write(rem.getContent() + "\r\n");
                }
                //Flush the outputstream
                out.flush();
            }else{
                (new ErrorCommand(in, out, conn, "No messages")).execute();
            }
        }else{
            (new ErrorCommand(in, out, conn, "You are not logged on")).execute();
        }

        //capture adequet errors (No messages) or (You are not logged on)






    }
}