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
    public static BufferedReader in;
    public static BufferedWriter out;
    public static BufferedReader scanner;
    public static boolean connected;
    public static String username;
    public static boolean paranoidMode;
    public static final String DEFAULT_USERNAME = "Revolucionario Anónimo";
    
    //ip Baricco 79.37.41.186




    public Client() {
        port = 49160;
        connected = true;
        serverIp = "79.37.41.186";
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
        try{ out.write("SERVER_DISCONNECT" + "\n"); out.flush(); } catch(Exception e) { }
    }



    public static void sendMessage(String message) {
        if (message.isBlank()) { System.out.println("[Client] - Error, User Input Invalid"); return; }
        Message msg = new Message(username, "", message);
        System.out.println("[Client] - Sending: " + message);
        //NON TOGLIERE IL /n PERCHE' SERVE A FAR FUNZIONARE L'in.readLine() NEL SERVER
        try { out.write(username + ": " + message + "\n"); out.flush(); } catch (IOException e) { System.out.println("[Client] - Error, can't output to the Server"); }
    }

    public static String listen() {  
        String risposta = "";      
        System.out.println("[Client] - Waiting a message from the Server...");
        try { risposta = in.readLine();} catch (IOException e) {}
        return risposta;
    }


    public Socket connect() {
            System.out.println("[Client] - Trying to connect to the Server...");

            try { socket = new Socket(serverIp, port); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

            System.out.println("[Client] - Connected!");

            try {          
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
        System.out.println("CIAO SONO ALLA FINE");
        stopConnection();
    }

}