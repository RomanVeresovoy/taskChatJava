package lab;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {

    private static InetAddress addr;
    private static String username;
    private static String command;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream oos;
    private BufferedReader br;


    public void main() {

        br = new BufferedReader(new InputStreamReader(System.in)); //консоль
        System.out.println("Для регистрации клиента введите : /register client");
        System.out.println("Для регистрации агента введите :/register agent");
        System.out.println("Использйте команду /leave для завершения чата (доступно только для клиетов)");
        System.out.println("Используйте команду /exit для выхода из системы");
        System.out.print("Register now: ");

        try {

            int flag = 0;
            while (flag == 0) {
                command = br.readLine();
                if (command.equals("/register client")) {
                    username = "client";
                    flag = 1;
                } else if (command.equals("/register agent")) {
                    username = "agent";
                    flag = 1;
                } else
                    System.out.println("Неверная команда! Повторите ввод");
            }
            System.out.println("Welcome, " + username + ".");
        } catch (IOException e2) {
            System.err.println("Error I/O");
        }

        try {
            addr = InetAddress.getByName("localhost");
        } catch (UnknownHostException e1) {
            System.err.println("Unknow addres");
        }
        System.out.println("Address: " + addr);
        try {
            socket = new Socket(addr, Server.PORT);

            try {
                System.out.println("Socket: " + socket);
                Scanner scan = new Scanner(System.in);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                oos = new ObjectOutputStream(socket.getOutputStream());

                out.println(command);

                Thread thread = new Thread(new ReadMessage(in)); //поток чтения входящих сообщений
                thread.start();

                while (true) { //поток ввода сообщения
                    String msg = br.readLine();
                    if (msg.equals("/exit")) {
                        Message message = new Message(username, msg, new Date(), ReadMessage.idAddres, ReadMessage.idConnection);
                        oos.writeObject(message);
                        ReadMessage.idAddres = 0;
                        this.close();
                        break;
                    } else if (msg.equals("/leave") && username.equals("client")) {
                        Message message = new Message(username, msg, new Date(), ReadMessage.idAddres, ReadMessage.idConnection);
                        oos.writeObject(message);
                        ReadMessage.idAddres = 0;
                        System.out.println("Введите сообщение и мы подберем вам агента!");
                        continue;
                    }
                    Message message = new Message(username, msg, new Date(), ReadMessage.idAddres, ReadMessage.idConnection);
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                    System.out.println(message.getName() + " (" + dateFormat.format(message.gerDate())
                            + ") " + ": " + message.getMessage());
                    oos.writeObject(message);
                }
            } finally {
                System.out.println("Closing: " + socket);
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Can't connect to server");
        }
    }

    private void close() {
        try {
            br.close();
            out.close();
            oos.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Error!");
        }
    }

}
