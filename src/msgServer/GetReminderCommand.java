package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GetReminderCommand implements Command {
    private BufferedReader in;
    private BufferedWriter out;
    private MsgSvrConnection conn;

    public GetReminderCommand(BufferedReader in, BufferedWriter out, MsgSvrConnection serverConn) {
        this.in = in;
        this.out = out;
        this.conn = serverConn;
    }

    public void execute() throws IOException {
        String user = in.readLine();
        if (conn.getCurrentUser() != null &&
                conn.getCurrentUser().equals(user)) {
            Reminder r = null;
            if ((r = conn.getServer().getReminders().getNextReminder(user)) != null) {
                out.write("" + MsgProtocol.REMINDER + "\r\n");
                out.write("" + 1 + "\r\n");
                out.write(r.getDate() + "\r\n");
                out.write(r.getContent() + "\r\n");
                out.flush();
            } else {
                (new ErrorCommand(in, out, conn, "No messages")).execute();
            }
        } else {
            (new ErrorCommand(in, out, conn, "You are not logged on")).execute();
        }
    }
}
