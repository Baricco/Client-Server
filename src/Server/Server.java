import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static final String STOP_CONNECTION = "STOP_CONNECTION";
    public static final int port = 49160;
    private static boolean open;

    static ServerSocket serverSocket;
    static Socket clientSocket;

    static BufferedReader in;
    static BufferedWriter out;
    static ArrayList<Connection> connections;
    static String msg;

    public Server() {
        connections = new ArrayList<Connection>();
        msg = " ";
        open = true;
    }

    public static void stopConnection(int ID)
    {
        for(int i = 0;i < connections.size(); i++)
            if(connections.get(i).getID() == ID)
            {
                connections.remove(i);
                System.out.println("[Server] - Connection (" + ID +") ended");
            }
                
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
        
        Server server = new Server();
        server.connect();

    }
}