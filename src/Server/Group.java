import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;


public class Group {

    public ArrayList<Integer> membersId;
    private static Random random;
    private final String id;
    private final LocalDateTime expireDate;
    
    public void addMember(int id) { membersId.add(id); }

    public Group(String id, int expiration) {
        random = new Random();
        this.id = id;
        membersId = new ArrayList<Integer>();
        if (expiration == Integer.MAX_VALUE) this.expireDate = LocalDateTime.MAX;
        else this.expireDate = LocalDateTime.now().plusHours(expiration);   
    }

    public Group(String id) {
        random = new Random();
        this.id = id;
        membersId = new ArrayList<Integer>();
        this.expireDate = LocalDateTime.MAX;  
    }

    public String getId() { return this.id; }

    public static String genNewId() {
        String dict = "!?#$%&@0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ^";
        random = new Random();
        String newId = "";
        for (int i = 0; i < Server.ID_LENGTH; i++) newId += dict.charAt(random.nextInt(dict.length()));
        return newId; 
    }

    public void removeMember(int connectionId) { for (int i = 0; i < membersId.size(); i++) if (membersId.get(i).intValue() == connectionId) membersId.remove(connectionId); }

    @Override public String toString() { return "ID: " + this.id + "\nExpiration Date: " + this.expireDate + "\nMembers: " + this.membersId.size(); } 
}
