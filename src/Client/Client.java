import java.io.*;
import java.net.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Client extends Application {

    public static Socket socket;
    public static int port;
    public static String serverIp;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
    public static BufferedReader scanner;
    public static boolean connected;
    public static String username;
    public static boolean paranoidMode;
    
    public static final String SERVER_DISCONNECT = "SERVER_DISCONNECT";
    public static final String GROUP_REQUEST = "GROUP_REQUEST";
    public static final String JOIN_REQUEST = "JOIN_REQUEST";
    public static final String GROUP_SPECS = "GROUP_SPECS";
    public static final String DEFAULT_USERNAME = "Revolucionario Anónimo";
    public static final String ADMINISTRATOR_USERNAME = "ADMIN" + (char)7; //deve essere uguale alla variabile nel Server
    public static final String[] groupExpirations = { "1 hour", "3 hours", "6 hours", "12 hours", "24 hours", "7 days", "Permanent" }; 
    
    //ip Baricco 79.45.219.74


    public Client() {
        port = 49160;
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

    private static void stopConnection() { sendMessage(SERVER_DISCONNECT, ADMINISTRATOR_USERNAME); }

    public static void sendMessage(String message) { sendMessage(message, username); }

    public static void sendMessage(String message, String username) {
        if (message.isBlank()) { System.out.println("[Client] - Error, User Input Invalid"); return; }
        Message msg = new Message(username, "", message);
        System.out.println("[Client] - Sending: " + message);
        try { out.writeObject(msg); out.flush(); } catch (IOException e) { System.out.println("[Client] - Error, can't output to the Server"); }
    }

    public static Message listen() {  
        Message risposta = new Message();      
        System.out.println("[Client] - Waiting a message from the Server...");
            
        try { risposta = (Message)in.readObject();} catch (Exception e) {  }
        
        System.out.println("[Client] - Caught the Server Message: " + risposta.content);
        return risposta;
    }

    public static void ctrlMessage(String msg) {
        if (msg.startsWith(GROUP_REQUEST)) ctrlGroupRequestAnswer(msg.substring(GROUP_REQUEST.length()));
    }

    private static void ctrlGroupRequestAnswer(String msg) {
        if (msg.toLowerCase().isEmpty()) { System.out.println("[Client] - Requested Group doesn't Exist"); return; }
        if (fxmlController.OBSL_groups.contains(msg)) { System.out.println("[Client] - You alredy joined the Group"); return; }
        Client.sendMessage(JOIN_REQUEST + msg, ADMINISTRATOR_USERNAME);
        System.out.println("[Client] - Group " + msg + " joined Successfully");
        fxmlController.OBSL_groups.add(msg);
    }

    public Socket connect() {
            System.out.println("[Client] - Trying to connect to the Server...");

            try { socket = new Socket(serverIp, port); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

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
        listener.stop();
        stopConnection();
        System.out.println("[Client] - Connection Stopped");
    }

}