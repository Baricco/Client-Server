public interface KeyWords {

    //                  VALORI CONDIVISI
    public static final int PORT = 49160;
    public static final int ID_LENGTH = 5;
    public static final int GROUP_TIMEOUT = 744;
    public static final int PING_TIMEOUT = 20000;
    public static final int VERSION = 1;
    public static final String VERSION_REQUEST = "VERSION_REQUEST";
    public static final String SERVER_DISCONNECT = "SERVER_DISCONNECT";
    public static final String GROUP_REQUEST = "GROUP_REQUEST";
    public static final String MEMBER_NUMBER_CHANGED = "MEMBER_NUMBER_CHANGED";
    public static final String GROUP_DELETED = "GROUP_DELETED";
    public static final String JOIN_REQUEST = "JOIN_REQUEST";
    public static final String CREATE_GROUP_REQUEST = "CREATE_GROUP_REQUEST";
    public static final String LEAVE_GROUP_REQUEST = "LEAVE_GROUP_REQUEST";
    public static final String INCOGNITO_REQUEST = "INCOGNITO_REQUEST";
    public static final String DEFAULT_USERNAME = "Revolucionario Anónimo";
    public static final String ADMINISTRATOR_USERNAME = "ADMIN" + (char)7;
    public static final String[] groupExpirations = { "1 hour", "3 hours", "6 hours", "12 hours", "24 hours", "7 days", "Permanent" };

    //                  VALORI DIFFERENTI 
    public static final String SYNC = "SYNC";
    public static final Group GLOBAL_CHAT = new Group("00000");
}
