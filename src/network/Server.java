package network;

import java.io.*;
import java.net.*;


public class Server {
    private ServerSocket ss;
    private int numClient;


    public Server() {
        System.out.println("----Server----");

        try {
            ss = new ServerSocket(25565);
        } catch (IOException ex){
            System.out.println("IOException from Server Constructor");
        }
    }



    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while (numClient < 1) {
                Socket s = ss.accept();
                numClient++;
                System.out.println("Client #" + numClient + " has connected.");
                ServerSideConnection ssc = new ServerSideConnection(s, numClient);
                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("No longer accepting connections");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    public static class ServerSideConnection implements Runnable {
        private DataOutputStream dataOut;
        private final int clientID;

        public ServerSideConnection(Socket s, int id) {
            clientID = id;
            try {
                dataOut = new DataOutputStream(s.getOutputStream());
            } catch (IOException ex) {
                System.out.println("IOException from run() SSC");
            }
        }

        public void run() {
            try {
                dataOut.writeInt(clientID);
                dataOut.flush();

            } catch (IOException ex) {
                System.out.println("IOException from run() ServerSideConnection");
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.acceptConnections();
    }
}
