import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    public static final String STOP_CONNECTION = "STOP_CONNECTION";
    public static final int port = 49160;
    private static boolean open;

    ServerSocket serverSocket;
    Socket clientSocket;

    BufferedReader in;
    BufferedWriter out;
    ArrayList<Connection> connections;
    String msg;

    public Server() {
        connections = new ArrayList<Connection>();
        msg = " ";
        open = true;
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