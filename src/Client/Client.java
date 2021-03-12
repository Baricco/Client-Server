import java.io.*;
import java.net.*;

public class Client
{
    Socket socket;
    int port;
    String serverIp;
    DataInputStream in;
    DataOutputStream out;
    BufferedReader scanner;
    String msg;

    public Client() {
        this.port = 49160;
        this.serverIp = "127.0.0.1";
        scanner = new BufferedReader(new InputStreamReader(System.in));
    }

    public void communicate() {
            System.out.println("[2] - Type the message to write to the Server: ");
            try { msg = scanner.readLine(); } catch (IOException e) {  }
            
            System.out.println("[3] - Sending: " + msg + "...");
            try { out.writeBytes(msg + "\n"); } catch (IOException e) { System.out.println("Error, can't output to the server"); }
            
            System.out.println("[4] - Waiting Server's reply...");
            try { msg = in.readLine(); } catch (IOException e) { System.out.println("Error, can't get input from the server"); }
            
            System.out.println("[5] - Server's Reply: " + msg);
    }

    public Socket connect() {
            System.out.println("[0] - Trying to connect to the Server...");

            try { this.socket = new Socket(serverIp, port); } catch (IOException e) { System.out.println("Error, server unreachable"); return null;}

            System.out.println("[1] - Connected!");

            try {
            in = new DataInputStream(this.socket.getInputStream());
            out = new DataOutputStream(this.socket.getOutputStream());
            } catch (IOException e) { System.out.println("Error, the socket is invalid"); }

        return socket;
    }


    public static void main(String args[]) {
        Client client = new Client();
        if (client.connect() != null) client.communicate();
    }


}