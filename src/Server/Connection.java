import java.io.*;
import java.net.*;

public class Connection extends Thread {
    private boolean connected = false;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket clientSocket;
    public static int ID = 0;
    private int privateID;

    public Connection(Socket clientSocket) {
        
        privateID = ID;
        ID++;
        connected = true;
        this.clientSocket = clientSocket;
        try {
            out = new ObjectOutputStream(this.clientSocket.getOutputStream());
            in = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch(Exception e) { System.out.println("[Client " + privateID + "] - Connection Error!"); }

        
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

    public void closeConnection() { 
        this.connected = false;
        try { this.clientSocket.close(); } catch(IOException e) { System.out.println("Error, Server is Unable to close the Connection"); }
        
        this.stop();
    }


    @Override
    public void run() {
        new Listener().start();
    }

    public void reply(Message message) {
        System.out.println("[Client " + privateID + "] - Replying with: " + message.content);
        try { out.writeObject(message); out.flush(); } catch (IOException e) { System.out.println("[Client " + privateID + "] - Error, can't output to the client"); }
    }

    public void listen() {        
        Message risposta = new Message();      
        System.out.println("[Server] - Listening...");
        try { risposta = (Message)in.readObject(); } catch (Exception e) { System.out.println("[Server] - Error, Cannot read the Client Message: "); }
        System.out.println("[Server] - Caught the Client Message: " + risposta.content + " from Group: " + risposta.group);
        if (risposta.username.equals(Server.ADMINISTRATOR_USERNAME)) Server.ctrlMessage(risposta.content, this.privateID);
        else Server.addMessageInQueue(risposta);
    }



    public int getID() { return privateID; }

}