import java.time.LocalDateTime;

public class Group {
    private final String id;
    private int nMembers;
    private LocalDateTime expireDate;
    

    public Group(String id, int expiration) {
        this.id = id;
        nMembers = 1;
        if (expiration == Integer.MAX_VALUE) this.expireDate = LocalDateTime.MAX;
        else this.expireDate = LocalDateTime.now().plusHours(expiration);   
    }
}
