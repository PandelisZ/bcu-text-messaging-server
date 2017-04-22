package msgServer;

public class MsgProtocol {
  /* -------------- Commands --------------- */
    /**
     * client requests to login
     * Following lines are
     * username\r\n
     * password\r\n
     */
    public static final int LOGIN = 101;
    /**
     * Client requests logout
     * following line is:
     * username\r\n
     */
    public static final int LOGOUT = 102;
    /**
     * client requests send a message
     * Following lines are:
     * sender name\r\n
     * recipient name\r\n
     * content\r\n
     */
    public static final int SEND = 103;
    /**
     * client requests the number of messages
     * following lines are
     * username\r\n
     */
    public static final int MESSAGES_AVAILABLE = 104;
    /**
     * Client requests to get a single message
     * Following lines are:
     * username\r\n
     */
    public static final int GET_NEXT_MESSAGE = 105;
    /**
     * Client requests to get all messages
     * Following lines are:
     * username\r\n
     * Server responds by sending all messages for that user
     */
    public static final int GET_ALL_MESSAGES = 106;


    public static final int REGISTRATION = 107;

    public static final int UPDATEUSER = 108;

    /**
     *  Client requests to set a new reminder
     *  Following lines are:
     *  username\r\n
     *  reminder\r\n
     */
    public static final int SET_REMINDER = 110;

    /**
     *  Client requests to get the next set reminder
     *  Following lines are
     *  username\r\n
     *  Server responds by sending the next reminder for that user based on the timestamp
     */
    public static final int GET_NEXT_REMINDER = 111;

    /**
     * Client requests to get all the reminders they have set themselves
     * Following lines are
     * username\r\n
     * Server responds by sending all the reminders set
     */
    public static final int GET_ALL_REMINDERS = 112;

  /* -------------- Responses --------------- */
    /**
     * Server responds OK
     */
    public static final int OK = 200;
    /**
     * Server responds by sending one or more messages
     * following will be
     * An integer specifying number of messages terminated by \r\n
     * Then repeated for the number of messages are
     * sender terminated by \r\n
     * content terminated by \r\n
     */
    public static final int MESSAGE = 201;
    /**
     * Server responds by sending one or more reminders
     * following will be
     * An integer specifying number of reminders terminated by \r\n
     * Then repeated for the number of reminders are
     * time terminated by \r\n
     * content terminated by \r\n
     */
    public static final int REMINDER = 202;
    /**
     * The server sends an error message
     * Requires a one line error message terminated by \r\n
     */
    public static final int ERROR = 500;
}
