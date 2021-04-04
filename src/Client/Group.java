import java.util.Random;

public class Group {
    private final String id;
    private static Random random;
    

    public Group() {
        random = new Random();
        id = genNewId();
    }

    //bisogna modificare questa funzione perchè certi caratteri sono difficili da vedere e va spostata nel server in modo da controllare se l'id non sia già occupato
    private String genNewId() {
        String newId = "";
        for (int i = 0; i < 4; i++) newId += (char)(random.nextInt(90) + 33);
        return newId; 
    }

    public String getId() { return this.id; }

}
