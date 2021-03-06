package lab;

import java.util.*;

class Clients {

    public static int k = 0;
    public static String kk;
    private ArrayList<ServerHandler> list = new ArrayList<>();


    public boolean firstSendMess(String username, String message, int idClient) {
        synchronized (list) {
            for (ServerHandler serverHandler : list) {
                if (serverHandler.getUsername().equals(username) && serverHandler.isFree()) {
                    serverHandler.setFree(false);
                    k = serverHandler.getid();
                    kk = Integer.toString(k);
                    String m = message + "*" + idClient + "*";
                    serverHandler.sendMsg(m);
                    return true;
                }
            }
        }
        return false;
    }

    public void addUser(ServerHandler client) {
        list.add(client);
    }



    public void sendMess(int id, String username, String message) {
        synchronized (list) {
            for (ServerHandler serverHandler : list) {
                if (serverHandler.getUsername().equals(username) && serverHandler.getid() == id) {
                    serverHandler.sendMsg(message);
                    break;
                }
            }
        }
    }

    public void leaveClient(int id){
        for(ServerHandler serverHandler : list){
            if(serverHandler.getid() == id) {
                serverHandler.setFree(true);
                break;
            }
        }
    }


}