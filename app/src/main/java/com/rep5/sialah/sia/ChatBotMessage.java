package com.rep5.sialah.sia;

/**
 * Created by jiexun on 5/10/16.
 */

public class ChatBotMessage {

    private String sender;
    private String message;

    public ChatBotMessage (String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
