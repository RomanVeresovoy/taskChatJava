package lab;

import java.io.*;

class ReadMessage implements Runnable {

    public static int idAddres = 0;
    public static int idConnection;
    static int flag = 0;
    BufferedReader in;

    public ReadMessage(BufferedReader in) throws IOException {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String str;
                String s = in.readLine();
                if (s.equals("null")) {
                    System.out.println("Нет свободных агентов!");
                    continue;
                }
                if (flag == 0) {
                    String ss = s.substring(s.indexOf('*') + 1, s.lastIndexOf('*'));
                    idConnection = Integer.parseInt(ss);
                    flag++;
                    continue;
                }
                if (s.indexOf('*') != -1) {
                    str = s;
                    String idString = str.substring(str.indexOf('*') + 1, str.lastIndexOf('*'));
                    idAddres = Integer.parseInt(idString);
                    String s1 = s.substring(0, s.indexOf('*'));
                    System.out.println(s1);
                    continue;
                }
                System.out.println(s);
            }
        } catch (IOException e) {
            System.err.println("Error I/OO");
        }
    }
}