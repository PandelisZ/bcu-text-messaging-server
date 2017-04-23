package msgServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jason on 22/04/2017.
 */
public class ReminderTrackerThread extends Thread {

    private ArrayList<Reminder> reminders;
    private MessageServer server;

    public ReminderTrackerThread(MessageServer server){
        this.server = server;
        this.reminders = server.getReminders().getAll();
    }
    public void updateReminders(ArrayList<Reminder> reminders){
        this.reminders = reminders;
    }

    @Override
    public void run() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while(true){
            // Get time and compare each reminder time to it
            Date currentDate = new Date();
            Iterator<Reminder> iter = reminders.iterator();
            while(iter.hasNext()){
                Reminder r = iter.next();
                Date reminderDate = null;
                try {
                    reminderDate = format.parse(r.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(reminderDate != null) {
                    // Compare dates
                    if (currentDate.compareTo(reminderDate) >= 0) {
                        // need to find user and message them
                        String reminderUser = r.getOwner();
                        // Connected users
                        ArrayList<MsgSvrConnection> connections = server.getConnections();
                        for(MsgSvrConnection conn : connections) {
                            if (conn.getCurrentUser() != null && conn.getCurrentUser().equals(reminderUser)) {
                                conn.remindCurrentUser(r.getContent());
                            }
                        }
                        // Remove from object local reminders
                        iter.remove();
                        // Remove from server/database reminders
                        server.getReminders().removeReminder(r, server.getDatabase());
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
