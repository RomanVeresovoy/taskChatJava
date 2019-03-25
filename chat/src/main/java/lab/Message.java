package lab;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String message;
    private Date date;
    private int id = 0;
    private int i = 0;



    public Message(String username, String message, Date date2, int id, int i) {
        this.message = message;
        this.username = username;
        this.date = date2;
        this.id = id;
        this.i = i;
    }

    public String getName() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public Date gerDate() {
        return date;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getI() {
        return i;
    }


}