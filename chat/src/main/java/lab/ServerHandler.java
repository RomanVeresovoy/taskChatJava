package lab;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class ServerHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream ois;
    private String username;
    private String reverseName;
    private boolean Free = true;
    private String command;
    private static long idKey = 100;
    private long id = 0;

    public ServerHandler(){}

    public ServerHandler(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        ois = new ObjectInputStream(socket.getInputStream());
        start();
    }

    public void run() {
        try {
            command = in.readLine();
            if (command.equals("/register client")) {
                username = "client";
                reverseName = "agent";
            } else if (command.equals("/register agent")) {
                username = "agent";
                reverseName = "client";
            }
            synchronized (this) {
                idKey++;
                id = idKey;
            }
            this.sendMsg("*" + id + "*");
            while (true) {
                Message message = (Message) ois.readObject();
                if (message.getMessage().equals("/leave")) {
                    Server.clientController.leaveClient(message.getIdAddress());
                    continue;
                }
                if (message.getMessage().equals("/exit")) {
                    Server.clientController.removeClient(message.getId());
                    continue;
                }
                System.out.println("Socket: " + socket + " Message: " + message.getMessage());
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String complite = message.getName() + " (" + dateFormat.format(message.gerDate())
                        + ") " + ": " + message.getMessage();
                if (message.getIdAddress() == 0) {
                    if (Server.clientController.firstSendMess(reverseName, complite, message.getId()))
                        this.sendMsg("*" + ClientController.kk + "*");
                    else
                        this.sendMsg("null");
                } else
                    Server.clientController.sendMess(message.getIdAddress(), reverseName, complite);
            }
        } catch (IOException e) {
            System.err.println("Error I/O");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        } finally {
            System.out.println("Closing: " + socket);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }

    public void sendMsg(String message) {
        out.println(message);
        out.flush();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFree() {
        return Free;
    }

    public void setFree(boolean free) {
        Free = free;
    }

    public long getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
