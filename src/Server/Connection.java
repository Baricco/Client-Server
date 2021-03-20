import java.io.*;
import java.net.*;

public class Connection extends Thread {
    private boolean connected = false;
    private BufferedReader in;
    private BufferedWriter out;
    public static int ID = 0;
    private int privateID;
    private String msg;

    public Connection(Socket clientSocket) {
        
        privateID = ID;
        ID++;
        connected = true;
        try {
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch(Exception e){System.out.println("[Client " + privateID + "] - Connection Error!");}

        
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

    public void reply(String message) {
        System.out.println("[Client " + privateID + "] - Replying with: " + message);
        
        try { out.write(message + "\n"); out.flush(); } catch (IOException e) { System.out.println("[Client " + privateID + "] - Error, can't output to the client"); }
    }

    public void listen() {        
        String risposta = null;      
        
        try { risposta = in.readLine(); } catch (IOException e) { }
        System.out.println("arrivato: " + risposta);
        if (risposta.equals(Server.SERVER_DISCONNECT)) Server.stopConnection(this.privateID);
        if (!risposta.equals(null)) Server.addMessageInQueue(risposta);
 
    }



    public int getID() { return privateID; }

}