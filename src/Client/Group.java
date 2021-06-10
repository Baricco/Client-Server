import java.io.Serializable;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group implements Serializable{
    private SimpleStringProperty id;
    
    public SimpleStringProperty name;     

    private ObservableList<String> messages;

    private boolean applyMod, muted;

    public Group() { this("", ""); }

    public Group(String id, String name) { 
        this.id = new SimpleStringProperty(id);
        this.name= new SimpleStringProperty(name);
        this.messages = FXCollections.observableArrayList();
        this.applyMod = false;
        this.muted = true;
    }

    public Group(String id) { 
        this.id = new SimpleStringProperty(id);
        this.name= new SimpleStringProperty(id);
        this.messages = FXCollections.observableArrayList();
        this.applyMod = false;
        this.muted = true;
    }

    public void addMessage(Message msg) {
        Platform.runLater(() -> {
            try {
                messages.add(msg.username + ":  " +  msg.content);
            } catch (Exception e) { }
        });
    }

    public boolean isApplyMod() { return applyMod; }

    public void setApplyMod(boolean applyMod) { this.applyMod = applyMod; }

    public boolean isMuted() { return muted; }

    public void setMuted(boolean muted) { this.muted = muted; }

    public ObservableList<String> getMessages() { return messages; }

    public String getId() { return id.get(); }

    public String getName() { return name.get(); }

    public SimpleStringProperty idProperty() { return id; }

    public SimpleStringProperty nameProperty() { return name; }

    public void setName(String name) { this.name.set(name); }

}
