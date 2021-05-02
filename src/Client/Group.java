import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group {
    private SimpleStringProperty id;
    
    public SimpleStringProperty name;     

    private ObservableList<String> messages;

    public Group() { this("", ""); }

    public Group(String id, String name) { 
        this.id = new SimpleStringProperty(id);
        this.name= new SimpleStringProperty(name);
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

    public String getId() { return id.get(); }

    public String getName() { return name.get(); }

    public SimpleStringProperty idProperty() { return id; }

    public SimpleStringProperty nameProperty() { return name; }

    public void setName(String name) { this.name.set(name); }

}
