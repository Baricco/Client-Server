import java.io.*;
import java.net.*;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;

public class Client extends Application implements KeyWords {

    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static BufferedReader scanner;
    public static boolean connected;
    public static String username;
    public static boolean paranoidMode;
    public static HashMap<String, Group> groups;

    public static fxmlController ctrlRef;
    

    public Client() {
        connected = true;
        scanner = new BufferedReader(new InputStreamReader(System.in));
        username = DEFAULT_USERNAME;
        paranoidMode = false;
        groups = new HashMap<String, Group>();


        Runtime.getRuntime().addShutdownHook(new Thread("app-shutdown-hook") {
              @Override 
              public void run() { 
                stopConnection();
            }
        });
    }

    private static void stopConnection() { 
        sendMessage(SERVER_DISCONNECT); 
        try { out.close(); } catch(IOException e) { System.out.println("Error the Output Channel"); }
        try { in.close(); } catch(IOException e) { System.out.println("Error the Input Channel"); }
        connected = false;
        try { socket.close(); } catch(IOException e) { System.out.println("Error, Client is Unable to close the Connection"); }
    }

    public static void sendMessage(String message) { sendMessage(message, ADMINISTRATOR_USERNAME, ""); }

    public static void sendMessage(String message, String group) { sendMessage(message, username, group); }

    public static void sendMessage(String message, String username, String group) {
        if (message.isBlank()) { System.out.println("[Client] - Error, User Input Invalid"); return; }
        Message msg = new Message(username, group, message);
        System.out.println("[Client] - Sending: " + message + " in Group: " + group);
        try { out.writeObject(msg); out.flush(); } catch (IOException e) { System.out.println("[Client] - Error, can't output to the Server"); }
    }

    public static Message listen() {  
        Message answer = new Message();      
        System.out.println("[Client] - Waiting a message from the Server...");
            
        try { answer = (Message)in.readObject();} catch (Exception e) { System.out.println("[Client] - Error, Can't read from the Server"); }
        
        System.out.println("[Client] - Caught the Server Message: " + answer.content + " from Group: " + answer.group);
        return answer;
    }

    public static void addNewGroup(Group group) {
        groups.put(group.getId(), group);
        Platform.runLater(() -> { try { fxmlController.OBSL_groups.add(group); } catch (Exception e) { } });      
    }

    public static void ctrlMessage(String msg) {
        if (msg.startsWith(GROUP_REQUEST)) ctrlGroupRequestAnswer(msg.substring(GROUP_REQUEST.length()));
        if (msg.startsWith(CREATE_GROUP_REQUEST)) addGroupToQueue(msg.substring(CREATE_GROUP_REQUEST.length())); //SEMBRA CHE QUESTA RISPOSTA NON ARRIVI MAI DAL SERVER ANCHE SE IL SERVER LA SPEDISCE
        if (msg.startsWith(GROUP_DELETED)) deleteGroupfromQueue(msg.substring(GROUP_DELETED.length()));
    }

    private static void deleteGroupfromQueue(String groupId) {
        try { 
            Client.sendMessage(LEAVE_GROUP_REQUEST + groupId); 
            fxmlController.removeGroup(groupId);
            groups.remove(groupId);
            System.out.println("Group: " + groupId + " Succesfully removed");
        } catch(Exception e) { System.out.println("Group has already been removed"); }
        ctrlRef.changeGroup(fxmlController.OBSL_groups.get(fxmlController.OBSL_groups.size() - 1).getId());
    }
    
    private static void addGroupToQueue(String id) {
        try { addNewGroup(new Group(id, id)); } catch(Exception e) { System.out.println("Error, The Group might haven't been Added to the List"); }
        System.out.println("[Client] - Group was Created and Added to the Group List Successfully");
        
        //Notifica 
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification();
    
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle("Group Created Successfully!");
            tray.setMessage("You just created a group with the following id: " + id);
        
            tray.setNotificationType(NotificationType.SUCCESS);
        
            tray.showAndDismiss(Duration.millis(DURATION_MILLIS));
        });
        //Fine Notifica
    
    }

    private static void ctrlGroupRequestAnswer(String msg) {
        if (msg.toLowerCase().isEmpty()) { System.out.println("[Client] - Requested Group doesn't Exist"); return; }
        if (fxmlController.OBSL_groups.contains(groups.get(msg))) { System.out.println("[Client] - You alredy joined the Group"); return; }
        Client.sendMessage(JOIN_REQUEST + msg);
        System.out.println("[Client] - Group " + msg + " joined Successfully");
        addNewGroup(new Group(msg, msg));
    }

    public Socket connect() {
            System.out.println("[Client] - Trying to connect to the Server...");

            try { socket = new Socket(SERVER_IP, PORT); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

            System.out.println("[Client] - Connected!");

            try { 
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); }

        return socket;
    }

    @Override
	public void start (Stage stage) throws Exception {

		TabPane root = FXMLLoader.load(getClass().getResource("fxml.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
        stage.setTitle("Hasta la Revolucion Messaging Service");
		ctrlRef.stage = stage;
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
	}

    public static void leaveGroups() {
        String groupList = "";
        for (Group g : groups.values()) groupList += g.getId() + ",";
        sendMessage(LEAVE_GROUP_REQUEST + groupList);
    }

    public static void main(String args[]) {
        
        Client client = new Client();
        client.connect();
        Listener listener = new Listener();
        listener.start();
        launch(args);
        System.out.println("[Client] - Stopping connection");
        listener.disconnect();
        leaveGroups();
        stopConnection();
        System.out.println("[Client] - Connection Stopped");
    }

}