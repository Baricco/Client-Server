import java.io.*;
import java.net.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Client extends Application implements KeyWords {

    public static Socket socket;
    public static String serverIp;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static BufferedReader scanner;
    public static boolean connected;
    public static String username;
    public static boolean paranoidMode;
    
    
    //ip Baricco 79.45.219.74


    public Client() {
        connected = true;
        serverIp = "79.45.219.74";
        scanner = new BufferedReader(new InputStreamReader(System.in));
        username = DEFAULT_USERNAME;
        paranoidMode = false;

        Runtime.getRuntime().addShutdownHook(new Thread("app-shutdown-hook") {
              @Override 
              public void run() { 
                stopConnection();
            }
        });
    }

    private static void stopConnection() { 
        sendMessage(SERVER_DISCONNECT); 
        try { out.close(); } catch(IOException e) { System.out.println("Error Closing the Connection"); }
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

    public static void ctrlMessage(String msg) {
        if (msg.startsWith(GROUP_REQUEST)) ctrlGroupRequestAnswer(msg.substring(GROUP_REQUEST.length()));
        if (msg.startsWith(CREATE_GROUP_REQUEST)) addGroupToQueue(msg.substring(CREATE_GROUP_REQUEST.length()));
    }

    private static void addGroupToQueue(String id) {
        try { fxmlController.addNewGroup(id); } catch(Exception e) { System.out.println("Error, The Group might haven't been Added to the List"); }
        System.out.println("[Client] - Group was Created and Added to the Group List Successfully");
    }

    private static void ctrlGroupRequestAnswer(String msg) {
        if (msg.toLowerCase().isEmpty()) { System.out.println("[Client] - Requested Group doesn't Exist"); return; }
        if (fxmlController.OBSL_groups.contains(msg)) { System.out.println("[Client] - You alredy joined the Group"); return; }
        Client.sendMessage(JOIN_REQUEST + msg);
        System.out.println("[Client] - Group " + msg + " joined Successfully");
        fxmlController.addNewGroup(msg);
    }

    public Socket connect() {
            System.out.println("[Client] - Trying to connect to the Server...");

            try { socket = new Socket(serverIp, PORT); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

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
		stage.show();

	}


    public static void main(String args[]) {
        
        Client client = new Client();
        client.connect();
        Listener listener = new Listener();
        listener.start();
        launch(args);
        System.out.println("[Client] - Stopping connection");
        stopConnection();
        listener.stop();
        System.out.println("[Client] - Connection Stopped");
    }

}