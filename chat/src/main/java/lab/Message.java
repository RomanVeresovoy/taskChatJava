package lab;

import java.io.Serializable;
import java.util.*;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String message;
    private Date date;
    private int idAddress = 0;
    private int id = 0;

    public Message(String username, String message, Date date2, int idAddress, int id) {
        this.message = message;
        this.username = username;
        this.date = date2;
        this.idAddress = idAddress;
        this.id = id;
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

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getId() {
        return id;
    }


}