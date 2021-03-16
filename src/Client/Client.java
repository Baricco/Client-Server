
import java.io.*;
import java.net.*;

public class Client
{
    static Socket socket;
    static int port;
    static String serverIp;
    static BufferedReader in;
    static BufferedWriter out;
    static BufferedReader scanner;
    static String msg = "errore";
    static boolean connected;

    //ip Baricco 87.20.39.3

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




    public static void reply() {
        msg = "errore";
        try {msg = scanner.readLine(); scanner.reset();} catch (IOException e) {}
        System.out.println("[Client] - Replying with: " + msg);
        try { out.write(msg + "\n"); out.flush(); } catch (IOException e) { System.out.println("[Client] - Error, can't output to the client"); }
    }

    public static void listen() {  
        String risposta = "errore";      
        System.out.println("[Client] - Waiting a message from the Server...");
        try { risposta = in.readLine(); } catch (IOException e) { }
        System.out.println("[Client] - Message received: " + risposta);
 
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


    public static void main(String args[]) {
        Client client = new Client();
        Socket socket = client.connect(); 
        if(socket != null);
            while (connected) {
                reply();
                listen();
                
                if(msg.equals("SERVER_DISCONNECT"))
                    connected = false;
            }
        stopConnection();
    }


}