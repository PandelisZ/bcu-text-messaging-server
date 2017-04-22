package msgServer;

import java.util.GregorianCalendar;

/**
 * Class to model an individual reminder
 */
public class Reminder {
    private String owner;
    private String content;
    private GregorianCalendar date;

    /**
     * Construct a new object of type Reminder.  The current date and time
     * is stored in the message.
     *
     * @param String owner The username of the owner of the reminder
     * @param String content The content of the reminder
     */
    public Reminder(String owner, String content) {
        this.owner = owner;
        this.content = content;
        this.date = new GregorianCalendar();
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
        return date.getTime().toString();
    }
}
