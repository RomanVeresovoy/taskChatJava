package lab;

import java.util.*;

class ClientController {

    public static long k = 0;
    public static String kk;
    private List<ServerHandler> list = Collections.synchronizedList(new ArrayList<ServerHandler>());

    public boolean firstSendMess(String username, String message, int idClient) {
        ServerHandler serverHandler = getFreeClient(username);
        if (serverHandler != null) {
            serverHandler.setFree(false);
            k = serverHandler.getid();
            kk = Long.toString(k);
            String m = message + "*" + idClient + "*";
            serverHandler.sendMsg(m);
            return true;
        }
        return false;
    }

    public ServerHandler getFreeClient(String username) {
        synchronized (list) {
            for (ServerHandler serverHandler : list) {
                if (serverHandler.getUsername().equals(username) && serverHandler.isFree())
                    return serverHandler;
            }
        }
        return null;
    }


    public void addUser(ServerHandler client) {
        synchronized (list) {
            list.add(client);
        }
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

    public void leaveClient(int id) {
        synchronized (list) {
            for (ServerHandler serverHandler : list) {
                if (serverHandler.getid() == id) {
                    serverHandler.setFree(true);
                    serverHandler.sendMsg("Клиетн покинул чат.");
                    break;
                }
            }
        }
    }

    public void removeClient(int id) {
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getid() == id) {
                    list.remove(i);
                    break;
                }
            }
        }
    }
}