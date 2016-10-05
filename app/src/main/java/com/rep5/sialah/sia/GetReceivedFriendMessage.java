package com.rep5.sialah.sia;

/**
 * Created by jiexun on 5/10/16.
 */

public class GetReceivedFriendMessage implements Runnable {

    private SiaMessage siaMessage;

    public void setSiaMessage(SiaMessage siaMessage) {
        this.siaMessage = siaMessage;
    }

    @Override
    public void run() {
        FriendConversation.getInstance().addReceivedMessage(siaMessage);
    }

}