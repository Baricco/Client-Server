import java.io.*;
import java.net.*;

public class Connection extends Thread {
    private boolean connected = false;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public static int ID = 0;
    private int privateID;

    public Connection(Socket clientSocket) {
        
        privateID = ID;
        ID++;
        connected = true;
        try {

        System.out.println("Buono il tailandese");

        in = new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        System.out.println("Ho mangiato Troppo");

        } catch(Exception e){ System.out.println("[Client " + privateID + "] - Connection Error!"); }

        
    }



    public class Listener extends Thread {

        @Override
        public void run() {   
            while(connected) {
                listen();
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }
    }


    @Override
    public void run() {
        new Listener().start();
    }

    public void reply(Message message) {
        System.out.println("[Client " + privateID + "] - Replying with: " + message);
        
        try { out.writeObject(message); out.flush(); } catch (IOException e) { System.out.println("[Client " + privateID + "] - Error, can't output to the client"); }
    }

    public void listen() {        
        Message risposta = new Message();      
        System.out.println("[Server] - Listening...");
        try { risposta = (Message)in.readObject(); } catch (Exception e) { System.out.println("[Server] - Error, Cannot read the Client Message: "); }
        System.out.println("[Server] - Caught the Client Message: " + risposta);
        if (risposta.content.equals(Server.SERVER_DISCONNECT)) Server.stopConnection(this.privateID);
        Server.addMessageInQueue(risposta);
 
    }



    public int getID() { return privateID; }

}