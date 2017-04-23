package msgServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Class to model a collection of Reminder.  The username of the owner
 * is the key that is used to store each reminder.  Therefore it is easy
 * to retrieve reminders set by a particular user.
 */
public class ReminderCollection {
    private Hashtable<String, Vector> reminders;
    private MessageServer server;
    /**
     * Construct a new empty Reminder Collection
     */
    public ReminderCollection(MessageServer server){
        this.reminders = new Hashtable<>();
        this.server = server;

        // Check database and load reminders
        Database db = server.getDatabase();
        //SQL query for retrieving previously set reminders for a user
        ResultSet rs = db.executeSQLQuery("SELECT `user`, `content`, `remindertime` FROM `reminders`");
        boolean setEmpty = true;
        try {
            while(rs.next()){
                setEmpty = false;
                String user = rs.getString("user");
                String content = rs.getString("content");
                String date = rs.getString("remindertime");
                System.out.println("Loaded: ");
                System.out.println(user + "\n" + content + "\n" + date);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date reminderDate = format.parse(date);
                Date currentDate = new Date();
                int secondsDifference = (int)((reminderDate.getTime() - currentDate.getTime())/1000)+1;
                if(reminders.containsKey(user)){
                    Vector<Reminder> usersReminders = reminders.get(user);
                    Reminder reminder = new Reminder(user, content, Integer.toString(secondsDifference) );
                    reminder.setId(usersReminders.size());
                    usersReminders.add(reminder);
                }else{
                    Vector<Reminder> userReminders = new Vector<>();
                    Reminder reminder = new Reminder(user, content, Long.toString(secondsDifference));
                    reminder.setId(userReminders.size());
                    userReminders.add(reminder);
                    reminders.put(reminder.getOwner(), userReminders);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(!setEmpty){
            System.out.println("Loaded reminders from database");
        }
    }

    /**
     * Command to add a new reminder to the collection
     *
     * @param Reminder reminder is the reminder to be added
     */
    synchronized void addReminder(Reminder reminder, Database db) {
        if (reminders.containsKey(reminder.getOwner())) {
            Vector<Reminder> msgList = (Vector) reminders.get(reminder.getOwner());
            reminder.setId(msgList.size());
            msgList.add(reminder);
        } else {
            Vector<Reminder> msgList = new Vector<>();
            reminder.setId(msgList.size());
            msgList.add(reminder);
            reminders.put(reminder.getOwner(), msgList);
        }
        // Add reminder to database
        db.executeSQLUpdate("INSERT INTO `reminders` (`user`, `content`, `remindertime`) VALUES ('"
                    + reminder.getOwner() + "', '" + reminder.getContent() + "', '" + reminder.getDate() + "')");
    }

    synchronized void removeReminder(Reminder reminder, Database db){
        Vector<Reminder> userReminders = reminders.get(reminder.getOwner());
        userReminders.remove(reminder);

        // remove reminder from database
        String sql = "DELETE FROM `reminders` WHERE `user`='"
                + reminder.getOwner() + "' AND `content`='" + reminder.getContent() + "' AND `remindertime`<='" + reminder.getDate() + "'";
        db.executeSQLUpdate(sql);
    }

    /**
     * Command to update a reminder already in the collection
     *
     * @param int id The id of the reminder to be updated
     * @param Reminder reminder is the new reminder content to overwrite the old
     */
    synchronized void updateReminder(int id, Reminder updatedReminder) {
        if (reminders.containsKey(updatedReminder.getOwner())) {
            Vector<Reminder> msgList = (Vector) reminders.get(updatedReminder.getOwner());
            Reminder oldReminder = msgList.elementAt(id);
            server.getDatabase().executeSQLUpdate("UPDATE `reminders` SET `content`='" + updatedReminder.getContent() + "', `remindertime`='" + updatedReminder.getDate() + "' WHERE " +
                    "`user`='" + oldReminder.getOwner() + "' AND `content`='" + oldReminder.getContent() + "' AND `remindertime`='" + oldReminder.getDate() + "'");
            updatedReminder.setId(id);
            msgList.set(id, updatedReminder);
        }
    }

    /**
     * Command to retrieve the last set reminder
     *
     * @param String user is the user who assigned the reminder
     * @return Reminder The last set reminder
     */
    synchronized public Reminder getNextReminder(String user) {
        Vector<Reminder> msgList = (Vector) reminders.get(user);
        if (msgList != null && !msgList.isEmpty()) {
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
     * Command to retrieve all the reminders that a user has set for himself
     *
     * @param String user Is the owner of the reminders
     * @return Reminder[] An array of reminders set by the user
     */
    synchronized public Reminder[] getAllReminders(String user) {
        Vector<Reminder> msgList = (Vector) reminders.get(user);
        if (msgList != null && !msgList.isEmpty()) {
            //reminders.remove(user);
            return msgList.toArray(new Reminder[msgList.size()]);
        }
        return null;
    }

    /**
     * Command to retrieve all reminders across all users
     *
     * @return Reminder[] remindersArray
     */
    synchronized public ArrayList<Reminder> getAll(){
        ArrayList<Reminder> remindersArray = new ArrayList<>();
        for(String key : reminders.keySet()){
            Vector<Reminder> reminderVector = reminders.get(key);
            for(Reminder r : reminderVector){
                remindersArray.add(r);
            }
        }
        return remindersArray;
    }
}
