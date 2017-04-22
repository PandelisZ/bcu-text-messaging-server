package msgServer;

import sun.util.calendar.Gregorian;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class to model an individual reminder
 */
public class Reminder {
    private String owner;
    private String content;
    private GregorianCalendar date;
    private GregorianCalendar remindTime; //time at which to remind the user

    /**
     * Construct a new object of type Reminder.  The current date and time
     * is stored in the message.
     *
     * @param String owner The username of the owner of the reminder
     * @param String content The content of the reminder
     */
    public Reminder(String owner, String content, String secondsToReminder) {
        this.owner = owner;
        this.content = content;
        this.date = new GregorianCalendar();

        int seconds = Integer.valueOf(secondsToReminder);

        this.remindTime = new GregorianCalendar();

        this.remindTime.add(GregorianCalendar.SECOND, seconds);
    }

    /**
     * Query to obtain the content of this message.
     *
     * @return String The content of this message
     */
    public String getContent() {
        return content;
    }


    /**
     * Query to obtain the username of the sender of this message
     *
     * @return String The username of the sender of the message
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Query to obtain the date and time this reminder was set
     *
     * @return String The date and time that the reminder was set
     */
    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(remindTime.getTime());
    }

}
