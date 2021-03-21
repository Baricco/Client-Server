import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static final String SERVER_DISCONNECT = "SERVER_DISCONNECT";
    public static final int port = 49160;
    private static boolean open;

    static ServerSocket serverSocket;
    static Socket clientSocket;

    static BufferedReader in;
    static BufferedWriter out;
    static ArrayList<Connection> connections;
    static String msg;
    static ArrayList<String> messageQueue = new ArrayList<String>();

    public Server() {
        connections = new ArrayList<Connection>();
        msg = " ";
        open = true;
    }

    public static void stopConnection(int ID) {
        for(int i = 0;i < connections.size(); i++)
            if(connections.get(i).getID() == ID) {
                connections.remove(i);
                System.out.println("[Server] - Connection " + ID + " ended");
            }
                
    }
    
    public static class Reply extends Thread {
        @Override 
        public void run() {
            while(open) {
                for(int i = 0;i<messageQueue.size();i++)
                    for(int j = 0;j<connections.size();j++) connections.get(j).reply(messageQueue.get(i));
                messageQueue.clear();
                try {Thread.sleep(10);} catch (InterruptedException e) {}
            }
        }

    }

    public static void addMessageInQueue(String message) {
        messageQueue.add(message);
    }

    public void connect() {
        System.out.println("[Server] - Initializing the Server...");

        try { serverSocket = new ServerSocket(port); } catch (IOException e) { System.out.println("[Server] - Error, port is invalid"); }
        System.out.println("[Server] - Server ready, listening on the port: " + port);
            while(open) {
                try { clientSocket = serverSocket.accept(); } catch(IOException e) { System.out.println("[Server] - Error, Server can't listen on the Socket"); return; }

                Connection connection = new Connection(clientSocket);
                connection.start();
                
                connections.add(connection);

                System.out.println("[Server] - New Connection");
            }
    }


    public static void main(String args[]) {
        new Reply().start();
        Server server = new Server();
        server.connect();
        

    }
}