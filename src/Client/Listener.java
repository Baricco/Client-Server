public class Listener extends Thread {

    @Override
    public void run() {
        while(Client.connected) {
            String message = Client.listen();
            fxmlController.addMessage(message);

            try { Thread.sleep(10); } catch (InterruptedException e) { }
        }
    }
}
