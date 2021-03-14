import java.io.*;
import java.net.*;

public class Server {

    ServerSocket server;
    Socket socketClient;

    int port;

    BufferedReader in;
    DataOutputStream out;

    String msg;

    public Server() {
        this.port = 49160;
    }

    public void reply() {
            try { msg = in.readLine(); } catch (IOException e) { }
            System.out.println("[4] - Message received: " + msg);
            String risposta = msg.toUpperCase();
            System.out.println("[5] - Replying with: " + risposta);
            try { out.writeBytes(risposta + "\n"); } catch (IOException e) { System.out.println("Error, can't output to the client"); }
    }


    public void connect() {
        try { System.out.println("[0] - Initializing the Server..." + InetAddress.getLocalHost()); }
        catch (UnknownHostException e) { System.out.println("Error, Invalid IP Address"); }
        try { server = new ServerSocket(port); } catch (IOException e) { System.out.println("Error, port is invalid"); }
        System.out.println("[2] - Server ready, listening on the port: " + port);
    }

    public void listen() {
            try { 
            socketClient = server.accept();
            server.close();
            } catch (IOException e) { }
            
            try {
            in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            out = new DataOutputStream(socketClient.getOutputStream());
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); }
            System.out.println("[3] - Waiting a message from the Client...");
    }

    public static void main(String args[]) {
        Server server = new Server();
        server.connect();
        
        server.listen();
        server.reply();
        try { Thread.sleep(100000); } catch(Exception e) { } // quando il programma si chiude, la connessione si chiude e dunque il client non riesce a recepire il messaggio
    }
}