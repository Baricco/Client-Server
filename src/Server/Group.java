import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;


public class Group implements Serializable {

    public ArrayList<Integer> membersId;
    private int membersNumber;
    private static Random random;
    private final String id;
    private LocalDateTime expireDate;
    private final boolean permanent;
    
    public void addMember(int id) { membersId.add(id); membersNumber++; }

    public void hideMember() { membersNumber--; }

    public void revealMember() { membersNumber++; }

    public Group(String id, int expiration) {
        random = new Random();
        this.id = id;
        membersNumber = 1;
        membersId = new ArrayList<Integer>();
        this.expireDate = LocalDateTime.now().plusHours(expiration);
        if (expiration == Server.GROUP_TIMEOUT) this.permanent = true; else this.permanent = false; 
    }

    public void startGroupCountdown() { this.expireDate = LocalDateTime.now().plusHours(Server.GROUP_TIMEOUT); }

    public void setPermanentGroup() { this.expireDate = LocalDateTime.MAX; }

    public int getMembersNumber() { return membersNumber; }

    public void setMembersNumber(int membersNumber) { this.membersNumber = membersNumber; }

    public Group(String id) {
        random = new Random();
        this.permanent = true;
        this.id = id;
        membersId = new ArrayList<Integer>();
        this.expireDate = LocalDateTime.now().plusHours(Server.GROUP_TIMEOUT);  
    }

    public Group(int id) { this(""); this.membersId.add(id); }

    public Group() { this(""); }

    public boolean isPermanent() { return this.permanent; }

    public String getId() { return this.id; }

    public boolean isEmpty() { return this.membersId.isEmpty(); }

    public LocalDateTime getExpireDate() { return this.expireDate; }

    public boolean hasExpired() { return this.expireDate.isBefore(LocalDateTime.now()); }

    public static String genNewId() {
        String dict = "!?#$%&@0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ^";
        random = new Random();
        String newId = "";
        for (int i = 0; i < Server.ID_LENGTH; i++) newId += dict.charAt(random.nextInt(dict.length()));
        return newId; 
    }

    public void removeMember(int connectionId) { for (int i = 0; i < membersId.size(); i++) if (membersId.get(i).intValue() == connectionId) { membersId.remove(i); membersNumber--; System.out.println("[Server] - Client n. " + connectionId + " was removed from group: " + this.id); }}

    @Override public String toString() { return "ID: " + this.id + "\nExpiration Date: " + this.expireDate + "\nMembers: " + this.membersId.size(); } 
}
