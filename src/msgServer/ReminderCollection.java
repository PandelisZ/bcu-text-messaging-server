package msgServer;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Class to model a collection of messages.  The username of the recipient
 * is the key that is used to store each message.  Therefore it is easy
 * to retrieve messages destined for a particular user.
 */
public class ReminderCollection {
    private Hashtable reminders;

    /**
     * Construct a new empty MessageCollection
     */
    public ReminderCollection() {
        reminders = new Hashtable();
    }

    /**
     * Command to add a new message to the collection
     *
     * @param Reminder reminder is the message to be added
     */
    synchronized void addReminder(Reminder reminder, Database db) {
        if (reminders.containsKey(reminder.getOwner())) {
            Vector msgList = (Vector) reminders.get(reminder.getOwner());
            reminder.setId(msgList.size());
            msgList.add(reminder);
        } else {
            Vector msgList = new Vector();
            reminder.setId(msgList.size());
            msgList.add(reminder);
            reminders.put(reminder.getOwner(), msgList);
        }
    }

    /**
     * Command to add a new message to the collection
     *
     * @param Reminder reminder is the message to be added
     */
    synchronized void updateReminder(int id, Reminder updatedReminder) {
        if (reminders.containsKey(updatedReminder.getOwner())) {
            Vector<Reminder> msgList = (Vector) reminders.get(updatedReminder.getOwner());
            updatedReminder.setId(id);
            msgList.set(id, updatedReminder);
        }
    }

    /**
     * Command to retrieve the oldest message waiting for a specific user.
     * The message is returned and also deleted from the collection.
     *
     * @param String user is the user who the message is addressed to
     * @return Message The oldest message addressed to that user
     */
    synchronized public Reminder getNextReminder(String user) {
        Vector msgList = (Vector) reminders.get(user);
        if (msgList != null) {
            Reminder reminder = (Reminder) msgList.lastElement(); //last element as if its a stack;
            //msgList.removeElementAt(0);
            if (msgList.size() == 0) {
                reminders.remove(user);
            }
            return reminder;
        } else {
            return null;
        }
    }

    /**
     * Query to retrieve the number of messages waiting for a specific user.
     *
     * @param String user is the user whose messages we are asking about
     *               8 @return int The number of messages waiting for this user
     */
    synchronized public int getNumberOfReminders(String user) {
        Vector msgList = (Vector) reminders.get(user);
        if (msgList != null) {
            return msgList.size();
        } else {
            return 0;
        }
    }

    /**
     * Command to retrieve all the messages waiting for a specific user.
     * The messages are deleted from the collection and
     * returned in an array of Messages.
     *
     * @param String user is the user who the messages are addressed to
     * @return Message[] An array of messages addressed to the user
     */
    synchronized public Reminder[] getAllReminders(String user) {
        Vector msgList = (Vector) reminders.get(user);
        if (msgList != null) {
            //reminders.remove(user);
            return ((Reminder[]) msgList.toArray(new Reminder[msgList.size()]));
        }
        return null;
    }
}
