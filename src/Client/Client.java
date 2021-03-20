import java.io.*;
import java.net.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Client extends Application {

    static Socket socket;
    static int port;
    static String serverIp;
    static BufferedReader in;
    static BufferedWriter out;
    static BufferedReader scanner;
    static boolean connected;
    
    //ip Baricco 87.20.39.3



    public static class Listener extends Thread {

        @Override
        public void run() {
            while(connected) {
                String message = listen();
                if(message != null) fxmlController.addMessage(message);

                try {Thread.sleep(10);} catch (InterruptedException e) { }
            }
        }
    }
    public Client() {
        port = 49160;
        connected = true;
        serverIp = "87.20.39.3";
        scanner = new BufferedReader(new InputStreamReader(System.in));

        Runtime.getRuntime().addShutdownHook(new Thread("app-shutdown-hook") {
              @Override 
              public void run() { 
                stopConnection();
            }
        });
    }

    private static void stopConnection()
    {
        try{ out.write("SERVER_DISCONNECT" + "\n"); out.flush(); } catch(Exception e) { }
    }



    public static void reply(String message) {
        System.out.println("[Client] - Replying with: " + message);
        try { out.write(message + "\n"); out.flush(); } catch (IOException e) { System.out.println("[Client] - Error, can't output to the client"); }
    }

    public static String listen() {  
        String risposta = null;      
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
        System.out.println("ciao");
        listener.stop();
        stopConnection();
    }

}