public class Message {
    public String content;
    public String group;
    public String username;
    
    public Message(String username, String group, String content) {
        this.content = content;
        this.group = group;
        this.username = username;
    }

    
}