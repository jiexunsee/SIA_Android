package com.rep5.sialah.sia;

/**
 * Created by jiexun on 4/10/16.
 */

public class GetReceivedMessage implements Runnable {

    private SiaMessage siaMessage;

    public void setSiaMessage(SiaMessage siaMessage) {
        this.siaMessage = siaMessage;
    }

    @Override
    public void run() {
        ChatBot.getChatBotInstance().addReceivedMessage(siaMessage);
    }

}
