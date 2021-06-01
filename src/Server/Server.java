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
        if (msg.startsWith(GROUP_REQUEST)) try { messageQueue.add(searchGroup(msg.substring(GROUP_REQUEST.length()))); System.out.println(messageQueue.get(messageQueue.size() - 1).toString()); } catch(GroupNotFoundException e) { System.out.println(e.getMessage()); }
        if (msg.startsWith(JOIN_REQUEST)) joinGroup(msg.substring(JOIN_REQUEST.length()), id); 
        if (msg.startsWith(LEAVE_GROUP_REQUEST)) leaveGroups(msg.substring(LEAVE_GROUP_REQUEST.length()), id);
    }

    private static void leaveGroups(String groupIdList, int connectionId) {
        String[] groupList = groupIdList.split(",");
        for (int i = 0; i < groupList.length; i++) { 
            groups.get(groupList[i]).removeMember(connectionId);
            if (groups.get(groupList[i]).membersId.size() == 0) {
                if (groups.get(groupList[i]).isPermanent()) groups.get(groupList[i]).startGroupCountdown();
                else groups.remove(groups.get(groupList[i]).getId());
            }
            System.out.println("[Server] - Connection " + connectionId + " has abandoned the Group " + groupList[i]);
        }
    }

    private static void joinGroup(String id, int connectionId) { 
        if (groups.get(id).membersId.isEmpty()) groups.get(id).setPermanentGroup();
        groups.get(id).addMember(connectionId);
        System.out.println("[Server] - Connection " + connectionId + " has joined the Group: " + id);
    }

    private static Message searchGroup(String id) throws GroupNotFoundException {
        if (groups.get(id) == null) throw new GroupNotFoundException();
        return new Message(ADMINISTRATOR_USERNAME, GLOBAL_CHAT.getId(), GROUP_REQUEST + id);
    }

    public static void createNewGroup(int expiration, int connectionId) {
        String id;
        do { id = Group.genNewId(); } while(groups.get(id) != null);
        Group temp = new Group(id, expiration);
        temp.membersId.add(connectionId);
        groups.put(id, temp);
        connections.get(connectionId).reply(new Message(ADMINISTRATOR_USERNAME, CREATE_GROUP_REQUEST + id));
        System.out.println("[Server] - Group " + id + " Created Succesfully");
    }

    private static void stopConnection(int id) {
        connections.get(id).closeConnection();
        connections.remove(id);
        System.out.println("[Server] - Connection " + id + " ended");               
    }

    public static class GroupManager extends Thread {
        @Override
        public void run() {
            while(true) {
                ArrayList<String> toRemove = new ArrayList<String>();
                ArrayList<Group> expiredGroups = new ArrayList<Group>();
                for (Group g : groups.values()) {
                    if (g.hasExpired() && !expiredGroups.contains(g)) { expiredGroups.add(g); addMessageInQueue(new Message(ADMINISTRATOR_USERNAME, g.getId(), GROUP_DELETED + g.getId())); }
                    for (int i = 0; i < expiredGroups.size(); i++) if (expiredGroups.get(i).isEmpty() && !toRemove.contains(g.getId())) toRemove.add(g.getId());
                }
                synchronized(SYNC) {   
                    for (String s : toRemove) { groups.remove(s); System.out.println("Group " + s + " Has been Eliminated"); }
                }
                for (int i = 0; i < 60; i++) try { sleep(10/*60000*/); } catch(Exception e) { } //CAMBIARE QUESTO SLEEP
            }
        }
    }    

    public static class Reply extends Thread {
        @Override 
        public void run() {
            while(open) {
                synchronized(SYNC) {
                    for(int i = 0; i < messageQueue.size(); i++) {
                    
                        System.out.println(messageQueue.get(i).group);
                        System.out.println(groups.get(messageQueue.get(i).group));
                        
                        for(int j = 0; j < groups.get(messageQueue.get(i).group).membersId.size(); j++) {
                            connections.get(groups.get(messageQueue.get(i).group).membersId.get(j)).reply(messageQueue.get(i));
                        }
                    }
                }
                messageQueue.clear();
                try { Thread.sleep(10); } catch (InterruptedException e) { }
            }
        }

    }

    public static void addMessageInQueue(Message message) { messageQueue.add(message); }

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
        Server server = new Server();
        groups.put(GLOBAL_CHAT.getId(), GLOBAL_CHAT);
        //PROBABILMENTE IL SERVER METTE NELLA MESSAGEQUEUE LA RISPOSTA ALLA GROUP REQUEST MA POI NON LA MANDA PERCHE' L'ALTRO THREAD
        //BLOCCA IN QUALCHE IL THREAD DELL'INVIO DEI MESSAGGI PER VIA DEL SYNC, VA RISOLTO
        groups.put("QWERT", new Group("QWERT")); //gruppo per fare i test
        new Reply().start();
        new GroupManager().start();
        server.connect();
    }
}