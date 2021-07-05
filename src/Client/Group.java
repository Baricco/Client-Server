import java.io.Serializable;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class Group implements Serializable{
    private SimpleStringProperty id;
    
    public SimpleStringProperty name;     

    private int numMembers;

    private ObservableList<String> messages;

    private ImageView mutedIcon;

    private boolean applyMod, muted, incognito;

    public Group() { this("", ""); }

    public Group(String id, String name) { 
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.messages = FXCollections.observableArrayList();
        this.numMembers = 0;
        this.applyMod = true;
        this.incognito = false;
        this.muted = false;
        this.mutedIcon = new ImageView();
    }

    public int getNumMembers() {
        return numMembers;
    }

    public void setNumMembers(int numMembers) {
        this.numMembers = numMembers;
    }

    public void addMember() { this.numMembers++; }

    public void removeMember() { this.numMembers--; }

    public Group(String id) { this(id, id); }
    
    public boolean isIncognito() { return this.incognito; }

    public void setIncognito(boolean incognito) { this.incognito = incognito; }

    public ImageView getMutedIcon() { return mutedIcon; }

    public void setMutedIcon(ImageView mutedIcon) { this.mutedIcon = mutedIcon; }

    public void addMessage(Message msg) { Platform.runLater(() -> { try { messages.add(msg.username + ":  " +  msg.content); } catch (Exception e) { } }); }

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