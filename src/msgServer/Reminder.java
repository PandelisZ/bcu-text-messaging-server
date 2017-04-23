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
     * Creates a Reminder object which will store the
     * alert time of the reminder as well as the content, owner and id
     *
     * @param owner String The owner of the reminder
     * @param content String The text contained in the reminder
     * @param secondsToReminder String The time in seconds to the desired alert
     */
    public Reminder(String owner, String content, String secondsToReminder) {
        this.owner = owner;
        this.content = content;
        this.date = new GregorianCalendar();

        this.id = -1; // for any new reminders that are yet to be added to the hashtable

        //format the time for the remindTime
        int seconds = Integer.valueOf(secondsToReminder);
        this.remindTime = new GregorianCalendar();
        this.remindTime.add(GregorianCalendar.SECOND, seconds);
    }

    /**
     * Getter for the content of a reminder
     *
     * @return String The content of this reminder
     */
    public String getContent() {
        return content;
    }


    /**
     * Getter for the owner of a reminder
     *
     * @return String The username of the owner of the reminder
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Setter for the id of a reminder
     * @param int id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the id of a reminder
     * @return int id
     */
    public String getId() {
        return Integer.toString(id);
    }

    /**
     * Getter for the alert time of a reminder.
     * Alert time is the time that the user has requested they be reminded
     * @return String alertTime
     */
    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(remindTime.getTime());
    }
}
