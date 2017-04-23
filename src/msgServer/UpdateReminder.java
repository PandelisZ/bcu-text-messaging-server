package msgServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UpdateReminder implements Command {
    private BufferedReader in;


    private BufferedWriter out;
    private MsgSvrConnection conn;

    public UpdateReminder(BufferedReader in, BufferedWriter out, MsgSvrConnection conn) {
        this.in = in;
        this.out = out;
        this.conn = conn;
    }

    public void execute() throws IOException {
        String id = in.readLine();
        String content = in.readLine();
        String seconds = in.readLine();
        if (conn.getCurrentUser() != null ){
            if (content != null && seconds != null && id != null) {


                Reminder r = new Reminder(conn.getCurrentUser(), content, seconds);

                try{
                    conn.getServer().getReminders().updateReminder(Integer.parseInt(id), r);
                    out.write("200\r\n");
                    out.flush();
                    return;
                }catch(ArrayIndexOutOfBoundsException e){
                    (new ErrorCommand(in, out, conn, "That reminder does not exist")).execute();
                }catch(NumberFormatException e){
                    (new ErrorCommand(in, out, conn, "That is not a valid id format")).execute();
                }
            } else {
                (new ErrorCommand(in, out, conn, "Error trying to set reminder")).execute();
            }
        } else {
            (new ErrorCommand(in, out, conn, "You are not logged in")).execute();
        }
    }
}
