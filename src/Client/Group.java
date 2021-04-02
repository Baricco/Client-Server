import java.util.Random;

public class Group {
    private final String id;
    private static Random random;
    

    public Group() {
        random = new Random();
        id = genNewId();
    }

    private String genNewId() {
        String newId = "";
        for (int i = 0; i < 4; i++) newId += (char)(random.nextInt(90) + 33);
        return newId; 
    }

    public String getId() { return this.id; }

}
