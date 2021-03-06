package lab;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 1234;
    public static ClientController clientController;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT, 0, InetAddress.getByName("localhost"));
        clientController = new ClientController();
        System.out.println("Started: " + s);
        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    System.out.println("Connected: " + socket);
                    clientController.addUser(new ServerHandler(socket));
                } catch (IOException e) {
                    System.out.println("Closing: " + socket);
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }
}