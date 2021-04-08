import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Server implements KeyWords {

    private static boolean open;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    private static HashMap<String, Group> groups;
    private static HashMap<Integer, Connection> connections;
    private static ArrayList<Message> messageQueue;

    public Server() {
        connections = new HashMap<Integer, Connection>();
        groups = new HashMap<String, Group>();
        messageQueue = new ArrayList<Message>();
        open = true;
    }

    public static void ctrlMessage(String msg, int id) {
        if (msg.equals(SERVER_DISCONNECT)) stopConnection(id);
        if (msg.startsWith(CREATE_GROUP_REQUEST)) createNewGroup(Integer.parseInt(msg.substring(CREATE_GROUP_REQUEST.length())), id);
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

    public static void createNewGroup(int expiration, int connectionId) {
        String id;
        do { id = Group.genNewId(); } while(groups.get(id) != null);
        groups.put(id, new Group(id, expiration));
        connections.get(connectionId).reply(new Message(ADMINISTRATOR_USERNAME, CREATE_GROUP_REQUEST + id));
        System.out.println("[Server] - Group " + id + " Created Succesfully");
    }

    private static void stopConnection(int id) {
        connections.get(id).closeConnection();
        connections.remove(id);
        System.out.println("[Server] - Connection " + id + " ended");                
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

        try { serverSocket = new ServerSocket(PORT); } catch (IOException e) { System.out.println("[Server] - Error, port is invalid"); }
        System.out.println("[Server] - Server ready, listening on the port: " + PORT);
            while(open) {
                try { clientSocket = serverSocket.accept(); } catch(IOException e) { System.out.println("[Server] - Error, Server can't listen on the Socket"); return; }

                Connection connection = new Connection(clientSocket);
                connection.start();
                connections.put(connection.getID(), connection);
                System.out.println("[Server] - New Connection");
            }
    }




    public static void main(String args[]) {
        new Reply().start();
        Server server = new Server();
        groups.put(GLOBAL_CHAT.getId(), GLOBAL_CHAT);
        System.out.println(groups.get("0000").toString());
        server.connect();
    }
}