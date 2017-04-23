package msgServer;

import sun.util.calendar.Gregorian;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.StringJoiner;

/**
 * Class to model an individual reminder
 */
public class Reminder {
    private String owner;
    private String content;
    private GregorianCalendar date;
    private GregorianCalendar remindTime; //time at which to remind the user
    private int id;


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

        this.id = -1;

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
//    public String getDate() {
//        return remindTime.getTime().toString();
//    }

    public void setContent(String content) {
        this.content = content;
    }

    public void updateTime(String secondsToReminder) {
        this.remindTime = new GregorianCalendar();

        int seconds = Integer.valueOf(secondsToReminder);
        this.remindTime.add(GregorianCalendar.SECOND, seconds);
    }

    public void setId(int id){
        this.id = id;
    }

    public String getId() {return Integer.toString(id); }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(remindTime.getTime());
    }
}
