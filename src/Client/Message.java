/*import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    public String content;
    public String group;
    public String username;
    
    public Message(String username, String group, String content) {
        this.content = content;
        this.group = group;
        this.username = username;
    }

    public Message() { 
        this.content = "";
        this.group = "";
        this.username = "";
    }

    
}*/
import java.io.Serializable;

public class Message implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String content;
    public Group group;
    public String username;
    
    public Message(String username, Group group, String content) {
        this.content = content;
        this.group = group;
        this.username = username;
    }

    public Message(String username, String content) {
        this(username, new Group(), content);
    }

    public Message() { 
        this.content = "";
        this.group = new Group();
        this.username = "";
    }

    @Override
    public String toString() { return "Message [content = " + content + ", group = " + group + ", username = " + username + "]"; }


    
}