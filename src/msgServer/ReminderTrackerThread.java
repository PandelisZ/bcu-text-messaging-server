package msgServer;

import java.util.GregorianCalendar;
import java.util.Hashtable;

/**
 * Created by jason on 22/04/2017.
 */
public class ReminderTrackerThread implements Runnable {

    private Reminder[] reminders;
    private MessageServer server;

    public ReminderTrackerThread(MessageServer server){
        this.server = server;
        this.reminders = server.getReminders().getAll();
    }

    @Override
    public void run() {
        while(true){
            // Get time and compare each reminder time to it
            for(Reminder r : reminders){
                r.getDate();
            }
        }
    }
}
