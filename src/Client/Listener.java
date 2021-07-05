import Manager.Decoder;

public class Listener extends Thread {
    
    private boolean connected;

    private static Decoder decoder;

    public Listener() { this.connected = true; decoder = new Decoder(); }

    public void disconnect() { this.connected = false; this.interrupt(); }

    @Override
    public void run() {
        while(this.connected) {
            Message message = Client.listen();
            if (message.username.equals(Client.ADMINISTRATOR_USERNAME)) Client.ctrlMessage(message.content);
            else { 
                message.content = decoder.decode(message.content);
                message.username = decoder.decode(message.username);
                try { fxmlController.addMessage(message); } catch(Exception e) { System.out.println("[Client] - can't write the message on the chat"); }
            }
            try { Thread.sleep(10); } catch (InterruptedException e) { }
        }
    }
}
