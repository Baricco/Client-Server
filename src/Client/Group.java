import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
    private final String id;
    
    public String name;     

    private ObservableList<String> messages;

    public Group() { this("", ""); }

    public Group(String id, String name) { 
        this.id = id;
        this.name = name;
        messages = FXCollections.observableArrayList();
    }

    public void addMessage(Message msg) {
        Platform.runLater(() -> {
            try {
                messages.add(msg.username + ":  " +  msg.content);
            } catch (Exception e) { }
        });
    }

    public ObservableList<String> getMessages() { return messages; }

    public String getId() { return this.id; }

}
