import java.io.*;
import java.net.*;

public class Connection extends Thread
{
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

    @Override
    public void run() {
        while (connected) {
            listen();
            reply();
            if(msg.equals("SERVER_DISCONNECT")) connected = false;
            msg = "SERVER_DISCONNECT";
        }
        Server.stopConnection(privateID);
    }

    public void reply() {
        String risposta = msg.toUpperCase();
        System.out.println("[Client " + privateID + "] - Replying with: " + risposta);
        try { out.write(risposta + "\n"); out.flush(); } catch (IOException e) { System.out.println("[Client " + privateID + "] - Error, can't output to the client"); }
    }

    public void listen() {        
        System.out.println("[Client " + privateID + "] - Waiting a message from the Client...");
        try { msg = in.readLine(); } catch (IOException e) { }
        System.out.println("[Client " + privateID + "] - Message received: " + msg);
 
    }

    public int getID()
    {
        return privateID;
    }

}