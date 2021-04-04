import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    public static final HashMap<String, Integer> expirationMap = new HashMap<String, Integer>();
    public static final String SERVER_DISCONNECT = "SERVER_DISCONNECT";
    public static final String ADMINISTRATOR_USERNAME = "ADMIN" + (char)7; //deve essere uguale alla variabile nel Client
    public static final String GROUP_SPECS = "GROUP_SPECS";
    public static final String JOIN_REQUEST = "JOIN_REQUEST";
    public static final String GROUP_REQUEST = "GROUP_REQUEST";


    public static final int port = 49160;
    private static boolean open;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    private static HashMap<String, Group> groups;
    private static ArrayList<Connection> connections;
    private static ArrayList<Message> messageQueue;

    public Server() {
        initHashMap();
        connections = new ArrayList<Connection>();
        groups = new HashMap<String, Group>();
        messageQueue = new ArrayList<Message>();
        open = true;
    }

    public static void ctrlMessage(String msg, int id) {
        if (msg.equals(SERVER_DISCONNECT)) stopConnection(id);
        if (msg.startsWith(GROUP_SPECS)) createNewGroup(msg.substring(GROUP_SPECS.length() - 1));
        if (msg.startsWith(GROUP_REQUEST)) messageQueue.add(searchGroup(msg.substring(GROUP_REQUEST.length())));
        if (msg.startsWith(JOIN_REQUEST)) joinGroup(msg.substring(JOIN_REQUEST.length())); 
    }

    private static void joinGroup(String id) { groups.get(id).addMember(); }

    private static Message searchGroup(String id) {
        String found = "";
        if (groups.get(id) != null) found = id; //Y --> group found, N --> group not found
        System.out.println(id +  "--\n" + groups.get(id));
        return new Message(ADMINISTRATOR_USERNAME, GROUP_REQUEST + found);
    }

    public static void createNewGroup(String msg) {
        String id;
        int expiration; 
        String[] temp = msg.split("\n");
        id = temp[1];
        expiration = expirationMap.get(temp[2]);
        groups.put(id, new Group(id, expiration));
        System.out.println("[Server] - Group " + id + " Created Succesfully");
    }

    private static void stopConnection(int ID) {
        for(int i = 0; i < connections.size(); i++)
            if(connections.get(i).getID() == ID) {
                connections.get(i).closeConnection();
                connections.remove(i);
                System.out.println("[Server] - Connection " + ID + " ended");
            }
                
    }
    
    public static class Reply extends Thread {
        @Override 
        public void run() {
            while(open) {
                for(int i = 0; i < messageQueue.size(); i++)
                    for(int j = 0; j < connections.size() ; j++) connections.get(j).reply(messageQueue.get(i));
                messageQueue.clear();
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }

    }

    public static void addMessageInQueue(Message message) {
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


    private void initHashMap() {
        expirationMap.put("1 hour", 1);
        expirationMap.put("3 hours", 3);
        expirationMap.put("6 hours", 6);
        expirationMap.put("12 hours", 12);
        expirationMap.put("24 hours", 24);
        expirationMap.put("7 days", 168);
        expirationMap.put("Permanent", Integer.MAX_VALUE);
    }

    public static void main(String args[]) {
        new Reply().start();
        Server server = new Server();
        server.connect();
        

    }
}