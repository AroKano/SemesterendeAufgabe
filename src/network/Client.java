package network;

// Client Side
import java.io.*;
import java.net.*;

public class Client {
    private int clientID;
    private ClientSideConnection csc;

    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            System.out.println("---Client---");
            try {
                socket = new Socket("localhost", 25565);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                clientID = dataIn.readInt();
                System.out.println("Connected to server as Client #" + clientID + ".");

            } catch (IOException ex) {
                System.out.println("IO Exception from ClientSideConnection constructor");
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();
    }
}
