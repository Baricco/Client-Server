package Manager;

public class NullInputException extends Exception {
    
    public NullInputException() {
        super("User Input is null or invalid");
    }
}
