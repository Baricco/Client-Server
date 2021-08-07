import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import Manager.Coder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.models.Location;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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

    private static Coder coder;

    public static fxmlController ctrlRef;

    public static boolean closingProgram;
    public static boolean firstConnectionError;

    private static MessageError disconnectedWindow;
    private static MessageError versionErrorWindow;

    private static Pane messageErrorWindow;

    private static Listener listener;
    private static Pane root;
    
    public static TabPane tabPane;



    public Client() {
        Client.connected = false;
        scanner = new BufferedReader(new InputStreamReader(System.in));
        username = DEFAULT_USERNAME;
        coder = new Coder();
        closingProgram = false;
        firstConnectionError = false;
        paranoidMode = false;
        groups = new HashMap<String, Group>();
        try {
            messageErrorWindow = FXMLLoader.load(getClass().getResource("disconnectedWindow.fxml"));

            messageErrorWindow.setVisible(false);
            
            
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static void stopConnection() {
        leaveGroups();
        sendMessage(SERVER_DISCONNECT); 
        try {
            try { out.close(); } catch(IOException e) { System.out.println("Error the Output Channel"); }
            try { in.close(); } catch(IOException e) { System.out.println("Error the Input Channel"); }
            Client.connected = false;
            try { socket.close(); } catch(IOException e) { System.out.println("Error, Client is Unable to close the Connection"); }
        } catch(NullPointerException e) { }

    }

    public static void sendMessage(String message) { sendMessage(message, ADMINISTRATOR_USERNAME, ""); }

    public static void sendMessage(String message, String group) { sendMessage(message, username, group); }

    public static void sendMessage(String message, String username, String group) {
        if (message.isBlank()) { System.out.println("[Client] - Error, User Input Invalid"); return; }
        Message msg = new Message(username, group, message);

        try {
            if (!msg.username.equals(ADMINISTRATOR_USERNAME)) {
                msg.content = coder.code(msg.content);
                msg.username = coder.code(msg.username);
            }
        } catch(Exception e) { viewNotification("User Input Error", "The message contains some invalid characters", false); return; }

        System.out.println("[Client] - Sending: " + message + " in Group: " + group);
        try { out.writeObject(msg); out.flush(); } catch (Exception e) { System.out.println("[Client] - Error, can't output to the Server"); Client.connected = false; waitAndConnect();}
    }

    public static Message listen() {  
        Message answer = new Message();      
        System.out.println("[Client] - Waiting a message from the Server...");
            
        try { answer = (Message)in.readObject();} catch (Exception e) { System.out.println("[Client] - Error, Can't read from the Server"); Client.connected = false; waitAndConnect(); }
        
        System.out.println("[Client] - Caught the Server Message: " + answer.content + " from Group: " + answer.group);
        return answer;
    }

    public static void addNewGroup(Group group) {
        groups.put(group.getId(), group);
        Platform.runLater(() -> { try { fxmlController.OBSL_groups.add(group); } catch (Exception e) { System.out.println("Can't add the Group in the List"); } });   

   

    }

    public static void ctrlMessage(String msg) {
        if (msg.startsWith(GROUP_REQUEST)) ctrlGroupRequestAnswer(msg.substring(GROUP_REQUEST.length()));
        if (msg.startsWith(CREATE_GROUP_REQUEST)) addGroupToQueue(msg.substring(CREATE_GROUP_REQUEST.length()));
        if (msg.startsWith(GROUP_DELETED)) deleteGroupfromQueue(msg.substring(GROUP_DELETED.length()));
        if (msg.startsWith(VERSION_REQUEST)) ctrlVersion(msg.substring(VERSION_REQUEST.length()));
        if (msg.startsWith(MEMBER_NUMBER_CHANGED)) changeGroupCount(msg.substring(MEMBER_NUMBER_CHANGED.length()));

    }

    public static void changeGroupCount(String msg) {
        Group group = groups.get(msg.substring(0, 5));
        String newNumber = msg.substring(5);
        group.setNumMembers(Integer.parseInt(newNumber));
        Tooltip.install(fxmlController.LSTV_rows.get(ctrlRef.LSTV_groups.getSelectionModel().getSelectedIndex()), new Tooltip("Group Id: " + fxmlController.selectedGroup.getId() + "\nMembers: " + fxmlController.selectedGroup.getNumMembers()));
    }

    private static void ctrlVersion(String versionString) {
        int version = Integer.parseInt(versionString);
        if (version != VERSION) {

            versionErrorWindow.setVisible(true);
            root.setMouseTransparent(true);
            versionErrorWindow.setMouseTrasparent(false);

            leaveGroups();
            sendMessage(SERVER_DISCONNECT); 
            listener.stop();
            try { out.close(); } catch(IOException e) { System.out.println("Error the Output Channel"); }
            try { in.close(); } catch(IOException e) { System.out.println("Error the Input Channel"); }
            try { socket.close(); } catch(IOException e) { System.out.println("Error, Client is Unable to close the Connection"); }

        }
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
    
    private static void addGroupToQueue(String msg) {
        String id = msg.substring(0, ID_LENGTH);
        String expiration = msg.substring(ID_LENGTH);
        try { addNewGroup(new Group(id, id)); } catch(Exception e) { System.out.println("Error, The Group might haven't been Added to the List"); }
        System.out.println("[Client] - Group was Created and Added to the Group List Successfully");
        
        for (Map.Entry<String, Integer> entry: ctrlRef.getExpirationMap().entrySet()) if (Integer.parseInt(expiration) == (entry.getValue())) { expiration = entry.getKey(); break; }
        final String temp;
        if (expiration.equals("Permanent")) temp = "Forever";
        else temp = expiration;
        viewNotification("Group Created Successfully!", "You just created a group with the following id: " + id + ",\nit will last " + temp, true);
    
    }

    public static void waitAndConnect() {
        try {
            if (closingProgram) { listener.stop(); return; }
            
            disconnectedWindow.setVisible(true);
            root.setMouseTransparent(true); 
            disconnectedWindow.setMouseTrasparent(false); 

            fxmlController.OBSL_groups.clear();

            ConnectionTryer tryer = new ConnectionTryer();
            System.out.println("[Client] - Reconnecting...");
            tryer.start();
            try { tryer.join(); } catch (InterruptedException e) { }
            if (firstConnectionError) { ctrlRef.initialize(); Client.firstConnectionError = false; }
            else {
                sendMessage(Client.JOIN_REQUEST + Client.GLOBAL_CHAT.getId());
                fxmlController.OBSL_groups.add(GLOBAL_CHAT);

                for (Group g : groups.values()) if(g.getId() != GLOBAL_CHAT.getId()) sendMessage(GROUP_REQUEST + g.getId()) ;

            }
            disconnectedWindow.setVisible(false);
            root.setMouseTransparent(false);
        } catch (Exception e) { }
        
    }

    public static class ConnectionTryer extends Thread {
        @Override
        public void run() {
            try { Thread.sleep(2000);} catch (InterruptedException e) { }
            while (!Client.connected) {
                if (closingProgram) return;
                Client.connected = connect();
                try { Thread.sleep(500);} catch (InterruptedException e) { }
            }
        }
    }

    private static void ctrlGroupRequestAnswer(String msg) {
        if (msg.toLowerCase().isEmpty()) { 
            System.out.println("[Client] - Requested Group doesn't Exist");
            viewNotification("Error Joining the Group", "The Group you tried to join doesn't exist,\nTry again with a different Id", false);
            return;
        }
        if (fxmlController.OBSL_groups.contains(groups.get(msg))) { System.out.println("[Client] - You alredy joined the Group"); return; }
        Client.sendMessage(JOIN_REQUEST + msg);
        System.out.println("[Client] - Group " + msg + " joined Successfully");
        addNewGroup(new Group(msg, msg));

        viewNotification("Group Joined Successfully!", "You just joined the group with the following id: " + msg, true);
    }

    public static void viewNotification(String title, String message, boolean type) {
        Platform.runLater(() -> {
            TrayNotification tray = new TrayNotification(new Location((ctrlRef.stage.getX() + (ctrlRef.stage.getWidth() / 2)) - 461 / 2, ctrlRef.stage.getY()));
    
            tray.setAnimationType(AnimationType.POPUP);
            tray.setTitle(title);
            tray.setMessage(message);
            if (type) tray.setNotificationType(NotificationType.SUCCESS); else tray.setNotificationType(NotificationType.ERROR);
            
            tray.showAndDismiss(Duration.millis(DURATION_MILLIS));
        });
    }

    public static boolean connect() {
            System.out.println("[Client] - Trying to connect to the Server...");

            try { socket = new Socket(SERVER_IP, PORT); } catch (IOException e) { System.out.println("Error, server unreachable"); return false;}

            System.out.println("[Client] - Connected!");

            try { 
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); return false; }

        return true;
    }

    @Override
	public void start (Stage stage) throws Exception {

        Client.connected = connect();

		root = FXMLLoader.load(getClass().getResource("fxml.fxml"));

        tabPane = ((TabPane)(root.getChildren().get(0)));
        ((Pane)tabPane.getSelectionModel().getSelectedItem().getContent()).getChildren().add(messageErrorWindow);
        ObservableList<Node> children = ((Pane)tabPane.getSelectionModel().getSelectedItem().getContent()).getChildren();
        MessageError.setRoot((Pane)((Pane)children.get(children.size() -1)));
        
        disconnectedWindow = new MessageError((Pane)((Pane)children.get(children.size() -1)).getChildren().get(0));
        versionErrorWindow = new MessageError((Pane)((Pane)children.get(children.size() -1)).getChildren().get(1));

		Scene scene = new Scene(root);
                
        
		stage.setScene(scene);
        stage.setTitle("Hasta la Revolucion Messaging Service");
		ctrlRef.stage = stage;
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent t) {
                Platform.exit();
                System.out.println("[Client] - Stopping connection");
                closingProgram = true;
                endProcess();
                System.out.println("[Client] - Connection Stopped");
                System.exit(0);
            }
        });

        listener.start();
        

    }

    public static void leaveGroups() {
        String groupList = "";
        for (Group g : groups.values())  groupList += g.getId() + ",";
        sendMessage(LEAVE_GROUP_REQUEST + groupList);
        System.out.println("[Client] - Sent Leave Group Request for the following groups: " + groupList);
    }

    public static void endProcess() { listener.disconnect(); stopConnection(); }

    public static void main(String args[]) {
        
        Client client = new Client();
        listener = new Listener();
        launch(args);
    }

}