package src.logic;

import src.elements.Product;

import java.io.Serializable;

public class ServerPacket implements Serializable {

    private Object object;
    private String message;
    private boolean isSuccessful;
    private boolean isMessage;

    public ServerPacket() {

    }

    public ServerPacket(Object obj, String message, boolean isSuccessful, boolean isMessage) {
        this.object = obj;
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.isMessage = isMessage;
        if (object == null & message == null) {
           this.message = "Packet is empty.";
           this.isSuccessful = false;
           this.isMessage = true;
        }
    }

    public Object getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsSuccessful() {
        return isSuccessful;
    }

    public boolean getIsMessage() {
        return isMessage;
    }

}
