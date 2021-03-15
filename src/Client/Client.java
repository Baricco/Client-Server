import java.io.*;
import java.net.*;

public class Client
{
    Socket socket;
    int port;
    String serverIp;
    static BufferedReader in;
    static BufferedWriter out;
    static BufferedReader scanner;

    //ip Baricco 87.20.39.3

    public Client() {
        this.port = 49160;

        this.serverIp = "87.20.39.3";
        scanner = new BufferedReader(new InputStreamReader(System.in));
    }

    public void sendMessage(String msg) {        
        System.out.println("[3] - Sending: " + msg + "...");
        try { out.write(msg + "\n"); out.flush();} catch (IOException e) { System.out.println("Error, can't output to the server"); return; }
    }

    private void getServerReply() {
        System.out.println("[4] - Waiting Server's reply...");
        
        String msg = "";
        
        try { msg = in.readLine(); } catch (IOException e) { e.printStackTrace(); System.out.println("Error, can't get input from the server"); return; }
        
        System.out.println("[5] - Server's Reply: " + msg);
    }

    private static String getUserInput() {
        System.out.println("[2] - Type the message to write to the Server: ");
        try { return scanner.readLine(); } catch (IOException e) { return null; }
    }

    public Socket connect() {
            System.out.println("[0] - Trying to connect to the Server...");

            try { this.socket = new Socket(serverIp, port); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

            System.out.println("[1] - Connected!");

            try {          
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); }

        return socket;
    }


    public static void main(String args[]) {
        Client client = new Client();
        Socket socket = client.connect(); 
        
        String msg = "";

        do {
            msg = getUserInput();
            client.sendMessage(msg);
            client.getServerReply();
        } while (!msg.equals("stop"));
        /*
        in linea di massima funziona ma il client
        manda al sever il messaggio "stop" quando vuole terminare la connessione
        e il server gli risponde e non ha senso e poi non si possono mandare le lettere 
        accentate (mi sa che è perchè in ASCII non ci sono le lettere accentate maiuscole)
        */
        client.sendMessage("STOP_CONNECTION");
    }


}