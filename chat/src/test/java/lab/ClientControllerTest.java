package lab;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {

    @Test
    void firstSendMess() {
    }

    @Test
    void getFreeClient() throws IOException {
        Socket s =  new Socket();
        List<ServerHandler> list = new ArrayList<ServerHandler>();
        ServerHandler sH = new ServerHandler(s);
        sH.setUsername("agent");
        sH.setFree(true);
        list.add(sH);
        sH.setUsername("agent");
        sH.setFree(false);
        list.add(sH);



    }

    @Test
    void addUser() {
        ServerHandler sH = new ServerHandler();
        ServerHandler sH1 = new ServerHandler();
        List<ServerHandler> list = new ArrayList<ServerHandler>();
        assertEquals(0, list.size());
        list.add(sH);
        assertEquals(1,list.size());
        list.add(sH1);
        assertEquals(2,list.size());
    }

    @Test
    void sendMess() {
    }

    @Test
    void leaveClient() {
    }

    @Test
    void removeClient() {
        ServerHandler sH = new ServerHandler();
        ServerHandler sH1 = new ServerHandler();
        List<ServerHandler> list = new ArrayList<ServerHandler>();
        list.add(sH);
        list.add(sH1);
        list.remove(1);
        assertEquals(1,list.size());
    }
}