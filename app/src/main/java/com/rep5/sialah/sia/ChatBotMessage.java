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
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

}
