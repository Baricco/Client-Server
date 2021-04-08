import java.time.LocalDateTime;
import java.util.Random;


public class Group {
    public static final int ID_LENGTH = 4;

    private static Random random;
    private final String id;
    private int nMembers;
    private final LocalDateTime expireDate;
    public String name;
    
    public void addMember() { nMembers++; }

    public Group(String id, int expiration) {
        random = new Random();
        this.id = id;
        nMembers = 1;
        if (expiration == Integer.MAX_VALUE) this.expireDate = LocalDateTime.MAX;
        else this.expireDate = LocalDateTime.now().plusHours(expiration);   
    }

    public Group(String id) {
        random = new Random();
        this.id = id;
        nMembers = 0;
        this.expireDate = LocalDateTime.MAX;  
    }

    public String getId() { return this.id; }

    //bisogna modificare questa funzione perchè certi caratteri sono difficili da vedere
    public static String genNewId() {
        random = new Random();
        String newId = "";
        for (int i = 0; i < ID_LENGTH; i++)
         newId +=
         (char)
         (random.nextInt(90)
          + 33);
        return newId; 
    }

    @Override public String toString() { return "ID: " + this.id + "\nExpiration Date: " + this.expireDate + "\nMembers: " + this.nMembers; } 
}
