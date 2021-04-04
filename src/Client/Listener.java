public class Listener extends Thread {

    @Override
    public void run() {
        while(Client.connected) {
            Message message = Client.listen();
            if (message.username.equals(Client.ADMINISTRATOR_USERNAME)) Client.ctrlMessage(message.content);
            else fxmlController.addMessage(message);

            try { Thread.sleep(10); } catch (InterruptedException e) { }
        }
    }
}
