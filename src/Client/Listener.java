public class Listener extends Thread {
    
    private boolean connected;

    public Listener() { this.connected = true; }

    public void disconnect() { this.connected = false; this.interrupt(); }

    @Override
    public void run() {
        while(this.connected) {
            Message message = Client.listen();
            if (message.username.equals(Client.ADMINISTRATOR_USERNAME)) Client.ctrlMessage(message.content);
            else try { fxmlController.addMessage(message); } catch(Exception e) { System.out.println("[Client] - can't write the message on the chat"); }
            
            try { Thread.sleep(10); } catch (InterruptedException e) { }
        }
    }
}
