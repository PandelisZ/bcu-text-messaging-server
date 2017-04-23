package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ReminderCommand implements Command {
    private BufferedReader in;


    private BufferedWriter out;
    private MsgSvrConnection conn;

    public ReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection conn) {
        this.in = in;
        this.out = out;
        this.conn = conn;
    }

    public void execute() throws IOException {
        String content = in.readLine();
        String seconds = in.readLine();
        if (conn.getCurrentUser() != null && seconds != null){
            if (content != null) {
                Reminder r = new Reminder(conn.getCurrentUser(), content, seconds);
                    conn.getServer().getReminders().addReminder(r, conn.getServer().getDatabase());
                    out.write("200\r\n");
                    out.flush();
                    conn.getServer().updateRemindersThread();
                    return;

            } else {
                (new ErrorCommand(in, out, conn, "Error trying to set reminder")).execute();
            }
        } else {
            (new ErrorCommand(in, out, conn, "You are not logged in")).execute();
        }
    }
}
