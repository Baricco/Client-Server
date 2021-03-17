
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.application.Application;

public class Client extends Application 
{

    //Cazzo metti tutte le variabili statiche nel Client Pedrazzi sei un danno
    static Socket socket;
    static int port;
    static String serverIp;
    static BufferedReader in;
    static BufferedWriter out;
    static BufferedReader scanner;
    static boolean connected;
    
    //ip Baricco 87.20.39.3



    public static class Listener extends Thread{

        Listener(){}
        @Override
        public void run()
        {
            while(connected)
            {
                String message = listen();
                if(message != null)
                    fxmlController.addMessage(message);

                try {Thread.sleep(10);} catch (InterruptedException e) {}
            }
        }
    }
    public Client() {
        this.port = 49160;
        connected = true;
        this.serverIp = "172.18.35.113";
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
        try{out.write("SERVER_DISCONNECT" + "\n"); out.flush();}catch(Exception e){}
    }



    public static void reply(String message) {
        //msg = "errore";
        //try {msg = scanner.readLine(); scanner.reset();} catch (IOException e) {}
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
            System.out.println("[Cleint] - Trying to connect to the Server...");

            try { this.socket = new Socket(serverIp, port); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

            System.out.println("[Cleint] - Connected!");

            try {          
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); }

        return socket;
    }

    @Override
	public void start (Stage stage) throws Exception {

		Pane root = FXMLLoader.load(getClass().getResource("fxml.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		
		
	}


    public static void main(String args[]) {
        
        Client client = new Client();
        Socket socket = client.connect(); 
        new Listener().start();
        launch(args);
        stopConnection();
    }

}